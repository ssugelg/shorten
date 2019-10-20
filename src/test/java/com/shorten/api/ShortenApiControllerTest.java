package com.shorten.api;

import com.shorten.config.SpringMockMvcTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("API 컨트롤러 테스트")
class ShortenApiControllerTest extends SpringMockMvcTestSupport {

    /**
     * 인크립트 정상 테스트
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("인크립트 값 테스트")
    void getEncrypt_001() throws Exception {

        String result = mockMvc.perform(
            post("/api/encrypt")
                .param("url", "http://www.amazon.com")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isOk()
        ).andReturn().getResponse().getContentAsString();

        assertEquals(8, result.length());
    }

    /**
     * 인크립트 GET 요청 에러 테스트
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("비정상적인 경로를 주입하여 인크립트 시도")
    void getEncrypt_002() throws Exception {

        mockMvc.perform(
            get("/api/encrypt")
                .param("url", "http://www.google.com")
        ).andExpect(
            status().isMethodNotAllowed()
        );
    }

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
    void getEncrypt_003(String domain) throws Exception{
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(
                post("/api/encrypt")
                    .param("url", domain)
            ).andReturn();
        });
    }
}