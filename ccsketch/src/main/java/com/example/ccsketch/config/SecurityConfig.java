package com.example.ccsketch.config;

import com.example.ccsketch.services.CustomOAuth2UserService;
import com.example.ccsketch.config.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHanlder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (JWT 사용 시)
                .authorizeHttpRequests(auth -> auth
                        // 아래 경로들은 인증 없이 접근 허용
                        .requestMatchers("/api/v1/signup", "/api/v1/login").permitAll()
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 커스텀 유저 서비스 등록
                        )
                        .successHandler(oAuth2SuccessHanlder) // 커스텀 성공 핸들러 등록
                );

        // JWT 필터 등록
        http.addFilterBefore(new JwtAuthenticationFilter(new com.example.ccsketch.util.JwtTokenProvider(
                System.getProperty("jwt.secret", "ThisIsADevelopmentOnlySecretKeyForJwtMustBeAtLeast32Bytes!!"),
                Long.parseLong(System.getProperty("jwt.expiration-ms", "3600000"))
        )), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}