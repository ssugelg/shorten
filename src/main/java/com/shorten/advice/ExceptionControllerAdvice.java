package com.shorten.advice;

import com.shorten.exception.ShortenServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = {ShortenServiceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object encryptError(HttpServletRequest request, Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Object noSuchUrl(HttpServletRequest request, Exception ex) {
        return exceptionExecute(request, HttpStatus.NOT_FOUND, ex, "error/404");
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object internalError(HttpServletRequest request, Exception ex) {
        return exceptionExecute(request, HttpStatus.INTERNAL_SERVER_ERROR, ex, "error/500");
    }

    private Object exceptionExecute(HttpServletRequest request, HttpStatus status, Exception ex, String page) {
        return exceptionExecute(request, status, ex, "", page);
    }

    private Object exceptionExecute(HttpServletRequest request, HttpStatus status, Exception ex, String message, String page) {

        ModelAndView mav = new ModelAndView(page);

        mav.addObject("message", message);
        mav.addObject("status", status);
        mav.addObject("stackTrace", getStackTrace(ex));

        return mav;
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();

        e.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }
}
