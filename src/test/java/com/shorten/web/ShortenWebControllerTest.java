package com.shorten.web;

import com.shorten.config.SpringMockMvcTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("웹 컨트롤러 테스트")
class ShortenWebControllerTest extends SpringMockMvcTestSupport {

    /**
     * Get으로 호출하면 다음과 같이 동작
     * <pre> - HTTP 코드는 200</pre>
     * <pre> - 반환될 뷰는 index</pre>
     * @see ShortenWebController#getPage()
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("인덱스 페이지 요청")
    void getPage_001() throws Exception {
        mockMvc.perform(
            get("/")
        ).andExpect(
            status().isOk()
        ).andExpect(
            view().name("index")
        );
    }

    /**
     * Post로 호출하면 다음과 같이 동작
     * <pre> - HTTP 상태코드는 Method Not Allowed(405)</pre>
     * @see ShortenWebController#getPage()
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("POST 로 요청시 에러처리")
    void getPage_002() throws Exception {
        mockMvc.perform(
            post("/")
        ).andExpect(
            status().isMethodNotAllowed()
        );
    }

    String testBeforeInsert(String decrypt) throws Exception {
        return mockMvc.perform(
            post("/api/encrypt")
                .param("url", decrypt)
        ).andReturn().getResponse().getContentAsString();
    }

    /**
     * 단축 경로 페이지 요청시 정상적으로 반환
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("단축 경로 페이지 요청")
    void moveTo_001() throws Exception {
        String decrypt = "http://www.google.com/" + ThreadLocalRandom.current().nextInt(1, 10000);
        String encrypt = testBeforeInsert(decrypt);

        mockMvc.perform(
            get("/" + encrypt)
        ).andExpect(
            redirectedUrl(decrypt)
        ).andExpect(
            status().isPermanentRedirect()
        );
    }

    /**
     * 페이지 요청은 Get 으로만 허용하였기 때문에 Post 서비스는 제공하지 않음
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("단축 경로 페이지 POST 요청시 에러 확인")
    void moveTo_002() throws Exception {
        String decrypt = "http://www.google.com/" + ThreadLocalRandom.current().nextInt(1, 10000);
        String encrypt = testBeforeInsert(decrypt);

        mockMvc.perform(
            post("/" + encrypt)
        ).andExpect(
            status().isMethodNotAllowed()
        );
    }


    /**
     * 오타 혹은 부적합한 문자열은 반환할 페이지가 없기 때문에 HTTP 상태를 404 로 리턴
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("없는 경로 요청시에 404 페이지로 반환")
    void moveTo_003() throws Exception {
        mockMvc.perform(
            get("/12345678")
        ).andExpect(
            status().isNotFound()
        );
    }

    /**
     * 오타 혹은 부적합한 문자열은 반환할 페이지가 없기 때문에 HTTP 상태를 404 로 리턴
     * @throws Exception ResultMatcher
     */
    @Test
    @DisplayName("잘못된 경로 정보 요청시에 404 에러 반환")
    void moveTo_004() throws Exception {
        mockMvc.perform(
            get("/123456")
        ).andExpect(
            status().isNotFound()
        );
    }
}