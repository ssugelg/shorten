package com.shorten.crypto.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 인크립트 알고리즘
 */
public class EncryptSHA256 implements EncryptService {
    @Override
    public String digest(Object value) {
        String result;

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");

            sha.update(((String) value).getBytes());

            byte bytes[] = sha.digest();

            StringBuffer buffer = new StringBuffer();

            for (byte b : bytes) {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16));
            }

            result = buffer.toString().substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

        return result;
    }
}
