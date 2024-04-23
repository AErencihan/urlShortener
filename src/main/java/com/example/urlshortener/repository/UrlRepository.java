package com.example.urlshortener.repository;

import com.example.urlshortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByKey(String key);

}
