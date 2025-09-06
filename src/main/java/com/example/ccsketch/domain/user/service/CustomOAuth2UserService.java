package com.example.ccsketch.domain.user.service;

import com.example.ccsketch.domain.user.entity.User;
import com.example.ccsketch.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        // OAuth2UserRequest를 이용해 OAuth2User 객체 생성
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // 클라이언트 등록 ID(kakao)와 사용자 이름 속성(id)을 가져옴
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2User에서 추출한 속성을 Map으로 변환
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        // 카카오 응답에서 프로필 정보 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");

        // DB에 사용자가 있는지 확인하고, 없으면 새로 저장(자동 회원가입)
        User user = saveOrUpdate(email, nickname);

        // DefaultOAuth2User 객체를 생성하여 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName);
    }

    private User saveOrUpdate(String email, String nickname) {
        Optional<User> userOptional = userRepository.findByLoginId(email); // 이메일을 loginId로 사용
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            // 필요하다면 여기서 기존 사용자의 정보(예: 닉네임)를 업데이트 할 수 있음
        } else {
            // 소셜 로그인은 비밀번호가 없으므로 임의의 값으로 설정
            user = User.builder()
                    .loginId(email)
                    .name(nickname)
                    .password("KAKAO_USER_PASSWORD") // 실제 사용되지 않는 임시 비밀번호
                    .build();
            userRepository.save(user);
        }
        return user;
    }
}