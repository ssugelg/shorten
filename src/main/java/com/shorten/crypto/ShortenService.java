package com.shorten.crypto;

import com.shorten.crypto.policy.Policy;
import com.shorten.crypto.policy.UrlPolicyValidator;
import com.shorten.redis.RedisEnCacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Shorten URL 서비스
 */
@Slf4j
@Service
public class ShortenService {

    @Autowired
    RedisEnCacheAdapter redisEnCacheAdapter;

    private ShortenFactory getFactoryResources(Policy policy) {
        return new ShortenFactory(policy);
    }

    private String getConvertUrl(ShortenFactory factory, String decrypt) {
        String shorten = (String) factory.encrypt(decrypt);

        boolean duplicate = Boolean.FALSE;

        while (redisEnCacheAdapter.contain(shorten)) {
            if (decrypt.equals(redisEnCacheAdapter.get(shorten))) {
                duplicate = Boolean.TRUE;
                break;
            }

            shorten = (String) factory.next().encrypt(decrypt);
        }

        if (!duplicate) {
            redisEnCacheAdapter.set(shorten, decrypt);
        }

        return shorten;
    }

    public String urlEncrypt(String decrypt) {
        ShortenFactory factory = getFactoryResources(new UrlPolicyValidator());

        return getConvertUrl(factory, decrypt);
    }

    public String urlDecrypt(String encrypt) {
        return redisEnCacheAdapter.get(encrypt);
    }
}
