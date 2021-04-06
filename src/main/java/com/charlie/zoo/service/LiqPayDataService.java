package com.charlie.zoo.service;

public interface LiqPayDataService {
    String generateData(String price,String orderId);
    String generateSignature(String data);
}
