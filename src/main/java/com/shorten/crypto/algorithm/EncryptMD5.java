package com.shorten.crypto.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 인크립트 알고리즘
 */
public class EncryptMD5 implements EncryptService {
    @Override
    public String digest(Object value) {
        String encrypt;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(((String) value).getBytes());

            byte[] byteData = md.digest();

            StringBuffer sb = new StringBuffer();

            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            encrypt = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("NoSuchAlgorithmException");
        }

        if (encrypt.isEmpty()) {
            throw new IllegalStateException("MD5 Encrypt is not working !!");
        }

        return encrypt.substring(0, 4) + encrypt.substring(encrypt.length() - 4);

    }
}
