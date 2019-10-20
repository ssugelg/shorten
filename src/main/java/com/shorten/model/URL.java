package com.shorten.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Redis Model
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class URL {

    private String encrypt;
    private String decrypt;

    public URL(String encrypt, String decrypt) {
        this.encrypt = encrypt;
        this.decrypt = decrypt;
    }
}
