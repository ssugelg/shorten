package com.shorten.crypto.policy;

import java.util.Iterator;

/**
 * 인크립트 정책 인터페이스
 */
public interface Policy {
    Iterator getAlgorithm();

    boolean verifyBefore(Object obj);
    boolean verifyAfter(Object obj);
    Object supplement(Object obj);
}
