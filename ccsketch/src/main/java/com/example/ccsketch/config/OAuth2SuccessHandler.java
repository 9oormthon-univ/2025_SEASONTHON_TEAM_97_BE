package com.example.ccsketch.config;

// import com.example.ccsketch.util.JwtTokenProvider; // jwt 프로바이더, 아직 안 만듦
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final com.example.ccsketch.util.JwtTokenProvider jwtTokenProvider; // JWT 토큰 생성기

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        if (email == null) {
            Object id = oAuth2User.getAttributes().get("id");
            email = "kakao-" + String.valueOf(id) + "@kakao.local";
        }

        // TODO: email을 기반으로 JWT 토큰 생성
        String token = jwtTokenProvider.createToken(email);

        // 프론트엔드로 리다이렉트할 URL을 만듭니다.
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/auth/success") // 프론트엔드 주소
                .queryParam("token", token)
                .build().toUriString();

        // 생성된 URL로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}