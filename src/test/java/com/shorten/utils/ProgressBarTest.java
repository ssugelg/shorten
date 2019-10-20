package com.shorten.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.time.Duration;

import static com.shorten.utils.ProgressBar.show;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;

/**
 * Redis 로 입출력 지연 시간용도로 사용되는 프로그래스바를 테스트
 */
class ProgressBarTest {

    /**
     * TODO 약 400 ~ 500ms 의 time loss 가 어디서 이루어지는지 확인 필요
     */
    @Test
    @DisplayName("프로그래스바 수행시간 확인")
    @Description("4000 ~ 5000 ms 이내에 실행 완료하기")
    void show_001() {
        assertTimeout(Duration.ofMillis(40 * 100 + 1000), () -> show(ProgressBar.Type.GET));
    }

    /**
     * TODO 콘솔에 찍힌 로그중에 `Done!` 이 포함되어 있는지 확인 필요
     */
    @Disabled
    @Test
    @DisplayName("정상적으로 로그가 출력되는지 체크")
    void show_002() {
        assertFalse(true);
    }
}