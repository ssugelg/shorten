package com.shorten.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;

class URLValidatorTest {

    /**
     * 정규식을 이용하여 유효한 URL 검증함
     * @param candidate 검증할 URL 항목
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "1",
        "http:",
        "http://",
        "httpx://www.google.com",
        "httpz://www.google.com",
        "http://com",
        "www.com",
        "google.com",
        "www.google.com"
    })
    @DisplayName("URL 유효성 검증")
    @Description("HTTP 프로토콜 및 3차 도메인도 필수로 포함하여야 함")
    void isValid(String candidate) {
        assertFalse(URLValidator.isValid(candidate));
    }
}