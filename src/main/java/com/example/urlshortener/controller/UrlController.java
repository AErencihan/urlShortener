package com.example.urlshortener.controller;


import com.example.urlshortener.dto.UrlDto;
import com.example.urlshortener.exception.GlobalException;
import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.request.RequestUrl;
import com.example.urlshortener.service.RateLimiterService;
import com.example.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final RateLimiterService rateLimiterService;


    @PostMapping("/short")
    public ResponseEntity<UrlDto> createShortUrl(@RequestBody RequestUrl requestUrl) {
        ShortUrl shortUrl = urlService.create(requestUrl);
        requestUrl.setKey(shortUrl.getKey());
        return new ResponseEntity<>(UrlDto.builder()
                .id(shortUrl.getId())
                .key(shortUrl.getKey())
                .url(shortUrl.getUrl())
                .build(), HttpStatus.CREATED);
    }

    @GetMapping("/get/{key}/{userId}")
    public void getUrlByKey(@PathVariable String key, @PathVariable String userId, HttpServletResponse response) {
        UrlDto shortUrl = urlService.findUrlByKey(key);
        rateLimiterService.limiterControl(userId);

        try {
            if (shortUrl != null) {
                response.sendRedirect(shortUrl.getUrl());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (IOException e) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error redirecting")
                    .build();
        }
    }


}
