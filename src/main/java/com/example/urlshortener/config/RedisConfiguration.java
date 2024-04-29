package com.example.urlshortener.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Value("${spring.data.redis.host}")
    String redisHost;

    @Value("${spring.data.redis.port}")
    int redisPort;


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .withCacheConfiguration("shortUrlCache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(1)))
                .build();
    }

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }


}
