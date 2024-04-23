package com.example.urlshortener.service;


import com.example.urlshortener.dto.UrlDto;
import com.example.urlshortener.exception.GlobalException;
import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.request.RequestUrl;
import com.example.urlshortener.util.RandomGenerateUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final RandomGenerateUrl randomGenerateUrl;
    private final UrlRepository urlRepository;


    public ShortUrl create(RequestUrl shortUrl) {
        if (shortUrl.getKey() == null || shortUrl.getKey().isEmpty()) {
            shortUrl.setKey(randomGenerateUrl.generateShortUrl());
        } else if (urlRepository.findByKey(shortUrl.getKey()).isPresent()) {
            throw GlobalException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Key already exist")
                    .build();
        }
        shortUrl.setKey(shortUrl.getKey().toUpperCase());

        return urlRepository.save(ShortUrl.builder()
                .url(shortUrl.getUrl())
                .key(shortUrl.getKey())
                .build());

    }


    public ShortUrl findUrlByKey(String key) {
        return urlRepository.findByKey(key).orElseThrow(
                () -> GlobalException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("Url not found")
                        .build()
        );
    }
}
