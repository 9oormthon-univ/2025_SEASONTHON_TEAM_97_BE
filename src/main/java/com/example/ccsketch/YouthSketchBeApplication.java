package com.example.ccsketch;

import com.example.ccsketch.domain.crawling.service.YouthCenterCrawlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YouthSketchBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouthSketchBeApplication.class, args);
    }

     @Bean
    public CommandLineRunner runCrawlOnStartup(YouthCenterCrawlService crawlService) {
         return args -> {
             crawlService.youthNotice(); // 스케줄러의 크롤링 메서드 호출
         };
    }

}
