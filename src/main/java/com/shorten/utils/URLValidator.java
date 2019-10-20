package com.shorten.utils;

import com.shorten.exception.ShortenServiceException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shorten.exception.ExceptionDefinition.ENCRYPT_ERROR_005;

/**
 * URL 유효성을 체크하는 유틸 클래스
 */
public class URLValidator {
    private static final String URL_REGEX = "^(?:(?:http(?:s)?|ftp)://)(?:\\S+(?::(?:\\S)*)?@)?(?:(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)(?:\\.(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)*(?:\\.(?:[a-z0-9\u00a1-\uffff]){2,})(?::(?:\\d){2,5})?(?:/(?:\\S)*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public static boolean isValid(String url) {

        if (url == null) {
            throw new ShortenServiceException(ENCRYPT_ERROR_005.getDescription());
        }

        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }
}
