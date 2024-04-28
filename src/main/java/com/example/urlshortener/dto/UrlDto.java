package com.example.urlshortener.dto;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto implements Serializable {

    private Long id;
    private String url;
    private String key;

}
