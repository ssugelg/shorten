package com.shorten.config;

import com.shorten.ShortenApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShortenApplication.class)
public abstract class SpringTestSupport {
}
