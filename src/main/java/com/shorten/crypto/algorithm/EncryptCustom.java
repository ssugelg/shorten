package com.shorten.crypto.algorithm;

/**
 * 내가 만든 알고리즘
 */
public class EncryptCustom implements EncryptService {
    @Override
    public String digest(Object value) {

        String seed = String.valueOf(value);

        byte[] bytes = seed.getBytes();

        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(b);
        }

        String concatStr = builder.toString();

        long sum = 0L;

        int unit = 8;

        for (int i = 0; i + unit < concatStr.length(); i += unit) {
            sum += Long.valueOf(concatStr.substring(i, i + unit));
        }

        return Long.toHexString(Long.valueOf(String.valueOf(sum).substring(0, 8)));
    }
}
