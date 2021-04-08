package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.service.LiqPayDataService;
import com.google.gson.Gson;
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
        params.put("sandbox", "0");
        params.put("result_url", "https://1299386eb5fa.ngrok.io/");
        return base64_encode(JSONObject.toJSONString(params));
    }

    @Override
    public String generateSignature(String data) {
        return base64_encode(sha1(privateKey+data+privateKey));
    }

    @Override
    public Map<String,String> decodeData(String data){
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        Gson gson = new Gson();
        return gson.fromJson(new String(decodedBytes), Map.class);
    }
}
