package com.shorten.crypto.algorithm;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 항상 임의의 값을 반환하는 알고리즘
 */
public class EncryptRandom implements EncryptService {
    private final int LENGTH = 8;

    private int getRandom(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    // a~z
    private String lowerChar() {
        return String.valueOf((char) getRandom(97, 122));
    }

    // A~Z
    private String upperChar() {
        return String.valueOf((char) getRandom(65, 90));
    }

    // 0~9
    private String numChar() {
        return String.valueOf(getRandom(0, 9));
    }

    @Override
    public String digest(Object value) {
        return IntStream.rangeClosed(0, LENGTH - 1)
            .mapToObj((i) -> {
                switch (getRandom(0, 2)) {
                    case 0: return lowerChar();
                    case 1: return upperChar();
                    default: return numChar();
                }
            }).collect(Collectors.joining());
    }
}
