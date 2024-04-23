package com.example.urlshortener.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomGenerateUrl {

    public String generateShortUrl(){
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 6;

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder shortUrl = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(allowedChars.length());
            shortUrl.append(allowedChars.charAt(index));
        }

            return shortUrl.toString();
    }




}
