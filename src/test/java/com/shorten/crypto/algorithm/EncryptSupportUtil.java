package com.shorten.crypto.algorithm;

import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * TDD 테스트를 위한 유틸
 */
public class EncryptSupportUtil {
    public static String randomNumber(int origin, int bound) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(origin, bound));
    }

    public static String randomUrl(String domain) {
        StringJoiner joiner = new StringJoiner("/");

        joiner.add(domain);

        IntStream.rangeClosed(1, 5).forEach(i -> joiner.add(randomNumber(1, 10000)));

        return joiner.toString();
    }
}
