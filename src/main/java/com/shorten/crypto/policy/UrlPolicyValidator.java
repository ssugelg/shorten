package com.shorten.crypto.policy;

import com.shorten.utils.URLValidator;

import java.util.*;

/**
 * URL 인크립트 정책
 */
public class UrlPolicyValidator extends AbstractPolicy {

    private final Comparator<UrlPolicyAlgorithm> compareTo = (p1, p2) -> {
        if (p1.getOrder() == p2.getOrder()) {
            return p1.ordinal() > p2.ordinal() ? 1 : -1;
        } else {
            return p1.getOrder() > p2.getOrder() ? 1 : -1;
        }
    };

    @Override
    public Iterator getAlgorithm() {
        return Arrays.stream(UrlPolicyAlgorithm.values())
            .sorted(compareTo)
            .map(UrlPolicyAlgorithm::getAlgorithm)
            .iterator();
    }

    @Override
    public boolean verifyBefore(Object obj) {
        return URLValidator.isValid((String) obj);
    }

    @Override
    public boolean verifyAfter(Object obj) {
        return ((String) obj).length() == 8;
    }

    @Override
    public Object supplement(Object obj) {
        String encrypt = (String) obj;

        if (encrypt.length() != 8) {
            if (encrypt.length() > 8) {
                encrypt = encrypt.substring(0, 4) + encrypt.substring(encrypt.length() - 4);
            } else {
                encrypt += new String(new char[8 - encrypt.length()]).replace('\0', '0');
            }
        }

        return encrypt;
    }
}
