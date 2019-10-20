package com.shorten.exception;

import lombok.*;

/**
 * Shorten URL 서비스 예외 클래스
 */
public class ShortenServiceException extends RuntimeException {
    public ShortenServiceException() {
        super();
    }

    public ShortenServiceException(String message) {
        super(message);
    }

    public ShortenServiceException(Throwable cause) {
        super(cause);
    }

    public ShortenServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
