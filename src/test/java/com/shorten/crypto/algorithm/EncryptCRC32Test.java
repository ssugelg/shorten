package com.shorten.crypto.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;

import java.time.Duration;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Slf4j
@Execution(CONCURRENT)
class EncryptCRC32Test {
    static EncryptService crc32;

    @BeforeAll
    static void BeforeAll() {
        crc32 = new EncryptCRC32();
    }

    /**
     * 서로 다른 도메인 인크립트시 중복 확률이 2% 미만이 되어야 한다.
     * 알고리즘을 통해 결과물이 나오는 시간으 200ms 이내로 수행되어야 한다.
     * 인크립트 결과물이 임의의 8자리(문자 + 숫자)라는 정책에 의거하여 중복은 필연적
     */
    @RepeatedTest(value = 100)
    @DisplayName("서로 다른 도메인 인크립팅")
    void digest_001() {
        assertTimeout(
            Duration.ofMillis(200),
            () -> {
                int times = 100 * 100;

                int duplicateCnt = times - (int) IntStream.range(0, times)
                    .mapToObj(i -> crc32.digest(EncryptSupportUtil.randomUrl("http://www.google.com")))
                    .distinct()
                    .count();

                double rate = (double) duplicateCnt / times * 100;

                log.info("{}% ({} / {})", String.format("%.2f", rate), String.format("%,d", duplicateCnt), String.format("%,d", times));

                assertTrue(Double.compare(2.f, rate) > 0);
            }
        );
    }

    /**
     * 같은 도메인으로 인크립트 했을때 같은 결과가 나와야 한다.
     */
    @RepeatedTest(value = 100)
    @DisplayName("같은 도메인 정보로 인크립팅")
    void digest_002() {
        int times = 100 * 100;

        String domain = EncryptSupportUtil.randomUrl("http://www.google.com");

        assertTrue(IntStream.range(0, times)
                .mapToObj(i -> crc32.digest(domain))
                .distinct()
                .count() == 1);

    }

}