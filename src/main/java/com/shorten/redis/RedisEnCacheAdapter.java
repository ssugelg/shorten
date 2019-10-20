package com.shorten.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring EnCache 를 이용한 Redis 입출력 어뎁터
 */
@Slf4j
@Component
public class RedisEnCacheAdapter {

    private static final String URL_CACHE = "urlCache";

    @Autowired
    RedisRunner redisRunner;

    @Transactional(value = "readonly")
    public boolean contain(String encrypt) {
        return redisRunner.contain(encrypt);
    }

    @CachePut(value = URL_CACHE, key = "#encrypt")
    public String set(String encrypt, String decrypt) {
        redisRunner.set(encrypt, decrypt);

        return decrypt;
    }

    @Cacheable(value = URL_CACHE, key = "#encrypt")
    public String get(String encrypt) {
        return redisRunner.get(encrypt);
    }
}
