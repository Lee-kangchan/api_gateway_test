package com.example.apt_gateway_test.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyData {

    private static String TOKEN_KEY;

    @Value("${token.key}")
    public void setTokenKey(String TOKEN_KEY) {
        PropertyData.TOKEN_KEY = TOKEN_KEY;
    }
    public static String getTokenKey() {
        return TOKEN_KEY;
    }

}
