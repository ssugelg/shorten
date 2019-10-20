package com.shorten.crypto;

import com.shorten.crypto.algorithm.EncryptService;
import com.shorten.crypto.policy.Policy;
import com.shorten.exception.ExceptionDefinition;
import com.shorten.exception.ShortenServiceException;

import java.util.Iterator;

/**
 * 정책을 실행할 Factory 클래스
 */
public class ShortenFactory {

    private final Policy policy;
    private final Iterator<EncryptService> algorithm;
    private EncryptService encryptModule;

    public ShortenFactory(Policy policy) {
        this.policy = policy;
        this.algorithm = policy.getAlgorithm();
        this.encryptModule = this.algorithm.next();
    }

    /**
     * 인크립트 알고리즘을 순회한다.
     * @return ShortenFactory
     */
    ShortenFactory next() {
        if (algorithm.hasNext()) {
            encryptModule = algorithm.next();
        }

        return this;
    }

    /**
     * 인크립트를 실행한다.
     * @param obj 인크립트 대상
     * @return 인크립트 결과
     */
    public Object encrypt(Object obj) {
        if (!policy.verifyBefore(obj)) {
            throw new ShortenServiceException(ExceptionDefinition.ENCRYPT_ERROR_003.getDescription());
        }

        Object result = policy.supplement(encryptModule.digest(obj));

        if (!policy.verifyAfter(result)) {
            throw new ShortenServiceException(ExceptionDefinition.ENCRYPT_ERROR_002.getDescription());
        }

        return result;
    }
}
