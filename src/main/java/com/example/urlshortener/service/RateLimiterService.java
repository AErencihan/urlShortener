package com.example.urlshortener.service;


import com.example.urlshortener.exception.GlobalException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RateLimiterService {

    Map<String, Bucket> cache = new HashMap<>();


    public void limiterControl(String key) {
        Bucket bucket = cache.computeIfAbsent(key, k -> createBucket());

        if (bucket.tryConsume(1)) {
            System.out.println("accept");
        } else {
            throw GlobalException.builder()
                    .message("limit is full")
                    .httpStatus(HttpStatus.TOO_MANY_REQUESTS)
                    .build();
        }
    }


    private Bucket createBucket() {
        //capacity = anlık gelen istek sayısı, refill = 1 dakikada 10 istek
        Bandwidth limit = Bandwidth.classic(4, Refill.intervally(10, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();

    }


}
