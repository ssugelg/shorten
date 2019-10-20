package com.shorten.crypto.policy;

/**
 * 인크립트 기본 정책 추상 클래스
 */
public abstract class AbstractPolicy implements Policy {

    @Override
    public boolean verifyBefore(Object obj) {
        return true;
    }

    @Override
    public boolean verifyAfter(Object obj) {
        return true;
    }

    @Override
    public Object supplement(Object obj) {
        return obj;
    }
}
