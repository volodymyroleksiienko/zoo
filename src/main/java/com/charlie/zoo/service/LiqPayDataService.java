package com.charlie.zoo.service;

import java.util.Map;

public interface LiqPayDataService {
    String generateData(String price,String orderId);
    String generateSignature(String data);
    Map<String,String> decodeData(String data,String signature);
}
