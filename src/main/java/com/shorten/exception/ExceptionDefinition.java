package com.shorten.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 예외 정책 항목
 */
@RequiredArgsConstructor
public enum ExceptionDefinition {

    COMMON_ERROR_001("1001", "Invalid argument", "유효하지 않는 파라메터 정보가 포함되어 있습니다."),
    COMMON_ERROR_002("1002", "Not supported type", "지원하지 않는 데이터 타입입니다."),
    COMMON_ERROR_003("1003", "Out of range", "값이 유효범위를 벗어났습니다."),

    SERVICE_ERROR_001("2001", "URL is not found", "요청하신 URL 경로를 찾을수 없습니다."),

    ENCRYPT_ERROR_001("3001", "Precondition is not correct", "사전조건이 부적합 합니다."),
    ENCRYPT_ERROR_002("3002", "Postcondition is not correct", "사후조건이 부적합 합니다."),
    ENCRYPT_ERROR_003("3003", "URL is invalid", "URL 형식이 부적합 합니다."),
    ENCRYPT_ERROR_004("3004", "The length of encrypt parameter string must be 8 degits", "인크립트 결과가 8자리가 아닙니다."),
    ENCRYPT_ERROR_005("3005", "Value is not null", "값이 비어있습니다."),

    SYSTEM_ERROR_001("9001", "DB error", "Redis 이용이 불가능한 상태입니다."),
    SYSTEM_ERROR_002("9002", "Cache error", "캐시 정보를 이용할 수 없습니다."),
    SYSTEM_ERROR_999("9999", "Unknown error", "알 수 없는 시스템 에러발생으로 인하여 작업이 중지되었습니다.");

    @Getter
    private final String code;

    @Getter
    private final String title;

    @Getter
    private final String description;

    @Override
    public String toString() {
        return String.format("[%s|%s] %s", code, title, description);
    }
}
