package com.shorten.crypto.algorithm;

import lombok.RequiredArgsConstructor;

import java.util.zip.CRC32;

/**
 * CRC32 인크립트 알고리즘
 */
@RequiredArgsConstructor
public class EncryptCRC32 implements EncryptService {

    @Override
    public String digest(Object value) {
        CRC32 crc32 = new CRC32();

        crc32.update(((String) value).getBytes());

        return Long.toHexString(crc32.getValue());
    }
}
