package com.shorten.api;

import com.shorten.crypto.ShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Shorten 서비스 API 컨트롤러
 */
@RestController
@RequestMapping(value = {"/api"})
public class ShortenApiController {

    @Autowired
    ShortenService shortenService;

    /**
     * URL 인크립트 서비스 API
     * @param decrypt 인크립트할 원본 URL
     * @return 인크립트 완료된 경로
     */
    @ResponseBody
    @PostMapping(value = {"/encrypt"})
    public String getEncrypt(@RequestParam(value = "url") String decrypt) {
        return shortenService.urlEncrypt(decrypt);
    }
}
