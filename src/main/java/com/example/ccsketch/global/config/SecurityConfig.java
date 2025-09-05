package com.example.ccsketch.global.config;

import com.example.ccsketch.domain.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectProvider<CustomOAuth2UserService> customOAuth2UserServiceProvider;
    private final ObjectProvider<OAuth2SuccessHandler> oAuth2SuccessHandlerProvider;
    private final ObjectProvider<ClientRegistrationRepository> clientRegistrationRepositoryProvider;
    private final com.example.ccsketch.global.util.JwtTokenProvider jwtTokenProvider; // 직접 참조

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/signup", "/api/v1/login", "/auth/success", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                );

        // H2 콘솔 프레임 허용
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        // OAuth2 로그인 설정은 관련 빈이 있을 때만 적용
        ClientRegistrationRepository clientRegistrationRepository = clientRegistrationRepositoryProvider.getIfAvailable();
        CustomOAuth2UserService customOAuth2UserService = customOAuth2UserServiceProvider.getIfAvailable();
        OAuth2SuccessHandler oAuth2SuccessHandler = oAuth2SuccessHandlerProvider.getIfAvailable();
        if (clientRegistrationRepository != null && customOAuth2UserService != null && oAuth2SuccessHandler != null) {
            http.oauth2Login(oauth2 -> oauth2
                    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                    .successHandler(oAuth2SuccessHandler)
            );
        }

        // JWT 필터 등록
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
