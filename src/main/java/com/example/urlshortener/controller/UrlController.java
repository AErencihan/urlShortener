package com.example.urlshortener.controller;


import com.example.urlshortener.dto.UrlDto;
import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.request.RequestUrl;
import com.example.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;


    @PostMapping("/short")
    public ResponseEntity<UrlDto> createShortUrl(@RequestBody RequestUrl requestUrl) {
        ShortUrl shortUrl = urlService.create(requestUrl);

        return new ResponseEntity<>(UrlDto.builder()
                .id(shortUrl.getId())
                .url(shortUrl.getUrl())
                .key(shortUrl.getKey())
                .build(), HttpStatus.CREATED);

    }


}
