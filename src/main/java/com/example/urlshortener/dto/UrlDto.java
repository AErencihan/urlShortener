package com.example.urlshortener.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto {

    private Long id;
    private String url;
    private String key;

}
