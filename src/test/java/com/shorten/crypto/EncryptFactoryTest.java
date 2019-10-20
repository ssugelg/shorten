package com.shorten.crypto;

import com.shorten.crypto.policy.Policy;
import com.shorten.crypto.policy.UrlPolicyAlgorithm;
import com.shorten.crypto.policy.UrlPolicyValidator;
import com.shorten.exception.ExceptionDefinition;
import com.shorten.exception.ShortenServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.context.annotation.Description;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@Slf4j
@Execution(SAME_THREAD)
@DisplayName("인크립트 팩토리 테스트")
class EncryptFactoryTest {

    static Policy policy;
    ShortenFactory factory;

    @BeforeAll
    @Description("URL 암호화 정책을 설정한다.")
    static void BeforeAll() {
        policy = new UrlPolicyValidator();
    }

    @BeforeEach
    @Description("인크립트 팩토리를 URL 암호화 정책으로 초기화 시켜준다.")
    void BeforeEach() {
        factory = new ShortenFactory(policy);
    }

    @Test
    @DisplayName("URL 인크립트 확인")
    void factory_001() {
        assertEquals(8, String.valueOf(factory.encrypt("http://www.google.com")).length());
    }

    /**
     * 오류 발생시 예상되는 Exception 클래스와 메세지가 포함되는지 확인
     */
    @Test
    @DisplayName("필터 오류 확인")
    void factory_002() {
        assertThrows(
            ShortenServiceException.class,
            () -> factory.encrypt("://www.google.com"),
            ExceptionDefinition.ENCRYPT_ERROR_003.getDescription()
        );
    }

    /**
     * 여러가지 방식의 인크립트 동작 확인
     */
    @Test
    @DisplayName("인크립트 알고리즘 별 동작")
    void factory_003() {
        Set<Object> sets = new HashSet<>();

        for (int i = 0; i < 10; ++i) {
            sets.add(factory.encrypt("http://www.google.com"));
            factory.next();
        }

        assertEquals(10, sets.size());
    }

    @RepeatedTest(100 * 10)
    @DisplayName("같은 도메인으로 인크립팅")
    void factory_004() {

        String domain = randomUrl("http://www.google.com");

        ShortenFactory factory1 = new ShortenFactory(policy);
        ShortenFactory factory2 = new ShortenFactory(policy);

        Arrays.stream(UrlPolicyAlgorithm.values())
            .forEach(t -> {
                if (t.equals(UrlPolicyAlgorithm.RANDOM)) {
                    assertNotEquals(factory1.encrypt(domain), factory2.encrypt(domain));
                } else {
                    assertEquals(factory1.encrypt(domain), factory2.encrypt(domain));
                }
                assertEquals(8, String.valueOf(factory1.encrypt(domain)).length());

                log.info("[{}] {} -> {}", t.name(), factory1.encrypt(domain), factory2.encrypt(domain));

                factory1.next();
                factory2.next();
            });
    }

    @RepeatedTest(value = 100 * 10)
    @DisplayName("다른 도메인으로 인크립팅")
    void factory_005() {

        ShortenFactory factory = new ShortenFactory(policy);

        Arrays.stream(UrlPolicyAlgorithm.values())
            .forEach(t -> {
                String result1 = (String) factory.encrypt(randomUrl("http://www.google.com"));
                String result2 = (String) factory.encrypt(randomUrl("http://www.google.com"));

                assertNotEquals(result1, result2);

                log.info("[CUSTOM] {} <-> {}", result1, result2);

                factory.next();
            });
    }

    @RepeatedTest(value = 100 * 100)
    @DisplayName("코드 커버리지 확인")
    void factory_006() {
        String domain1 = randomUrl("http://www.google.com");
        String domain2 = randomUrl("http://www.google.com");

        ShortenFactory factory1 = new ShortenFactory(policy);
        ShortenFactory factory2 = new ShortenFactory(policy);

        String result1, result2;

        for (UrlPolicyAlgorithm t : UrlPolicyAlgorithm.values()) {
            result1 = (String) factory1.encrypt(domain1);
            result2 = (String) factory2.encrypt(domain2);

            log.info("[{}] {} <-> {}", t.name(), result1, result2);

            if (!result1.equals(result2)) {
                break;
            }

            factory1.next();
            factory2.next();
        }
    }

    private String randomNumber(int origin, int bound) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(origin, bound));
    }

    private String randomUrl(String domain) {
        StringJoiner joiner = new StringJoiner("/");

        joiner.add(domain);

        IntStream.rangeClosed(1, 10).forEach(i -> joiner.add(randomNumber(1, 10000)));

        return joiner.toString();
    }
}