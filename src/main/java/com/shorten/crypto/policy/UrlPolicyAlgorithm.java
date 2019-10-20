package com.shorten.crypto.policy;

import com.shorten.crypto.algorithm.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * URL 인크립트 알고리즘 정책
 */
@Slf4j
@RequiredArgsConstructor
public enum UrlPolicyAlgorithm {
    CUSTOM(1, new EncryptCustom()),
    CRC32(2, new EncryptCRC32()),
    MD5(3, new EncryptMD5()),
    SHA256(4, new EncryptSHA256()),
    RANDOM(99, new EncryptRandom());

    @Getter
    public final int order;

    @Getter
    public final EncryptService algorithm;
}
