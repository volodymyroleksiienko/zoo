package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.service.LiqPayDataService;
import com.google.gson.Gson;
import com.liqpay.LiqPay;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.liqpay.LiqPayUtil.base64_encode;
import static com.liqpay.LiqPayUtil.sha1;

@Service
public class LiqPayDataServiceImpl implements LiqPayDataService {
    @Value("${liq.pay.security.publicKey}")
    private String publicKey;
    @Value("${liq.pay.security.privateKey}")
    private String privateKey;


    @Override
    public String generateData(String price, String orderId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "pay");
        params.put("amount", price);
        params.put("currency", "UAH");
        params.put("description", "description text");
        params.put("order_id", orderId);
        params.put("public_key", publicKey);
        params.put("version", "3");
        return base64_encode(JSONObject.toJSONString(params));
    }

    @Override
    public String generateSignature(String data) {
        return base64_encode(sha1(privateKey+data+privateKey));
    }

    @Override
    public Map<String,String> decodeData(String data,String signature){
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        Gson gson = new Gson();
        System.out.println(generateSignature(data));
        System.out.println(signature);
        if(generateSignature(data).equals(signature)) {
            return gson.fromJson(new String(decodedBytes), Map.class);
        }else {
            return null;
        }
    }
}
