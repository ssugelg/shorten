package com.shorten.web;

import com.shorten.crypto.ShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotNull;

/**
 * Shorten 서비스 웹 컨트롤러
 */
@Controller
public class ShortenWebController {

    @Autowired
    ShortenService shortenService;

    /**
     * 인크립트 서비스 페이지
     * @return 인덱스 페이지
     */
    @GetMapping(value = {"/"})
    public Object getPage() {
        return new ModelAndView("index");
    }

    /**
     * 페이지 리다이렉션
     * @param encrypt 인크립트된 주소
     * @return 리다이렉트 페이지
     */
    @GetMapping(value = {"/{encrypt}"})
    public Object moveTo(@NotNull @PathVariable String encrypt) {

        if (encrypt.length() != 8) {
            throw new IllegalArgumentException("The length of encrypt parameter string must be 8 degits");
        }

        RedirectView redirectView = new RedirectView();

        redirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        redirectView.setUrl(shortenService.urlDecrypt(encrypt));

        return redirectView;
    }
}
