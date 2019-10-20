package com.shorten.redis;

import com.shorten.model.URL;
import com.shorten.utils.ProgressBar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import static com.shorten.exception.ExceptionDefinition.SERVICE_ERROR_001;
import static com.shorten.utils.ProgressBar.Type.GET;
import static com.shorten.utils.ProgressBar.Type.SET;

/**
 * Redis 러너 클래스
 */
@Slf4j
@Component
public class RedisRunner {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void set(String encrypt, String decrypt) {
        ProgressBar.show(SET);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        operations.set(encrypt, new URL(encrypt, decrypt));
    }

    public boolean contain(String encrypt) {
        ProgressBar.show(GET);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        Object obj = operations.get(encrypt);

        return (obj != null);
    }

    public boolean contains(String ...encrypt) {
        boolean result = Boolean.FALSE;

        for (String key : encrypt) {
            result |= contain(key);
        }

        return result;
    }

    public String get(String encrypt) {
        ProgressBar.show(GET);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        URL url = (URL) operations.get(encrypt);

        if (url == null) {
            throw new IllegalArgumentException(SERVICE_ERROR_001.getDescription());
        }

        return url.getDecrypt();
    }
}
