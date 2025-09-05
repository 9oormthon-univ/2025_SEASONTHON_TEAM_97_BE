package com.example.ccsketch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = "spring.ai.openai.enabled=false")
class YouthSketchBeApplicationTests {

    @Test
    void contextLoads() {
    }

}
