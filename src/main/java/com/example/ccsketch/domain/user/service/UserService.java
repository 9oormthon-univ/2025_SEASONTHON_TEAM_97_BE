package com.example.ccsketch.domain.user.service;

import com.example.ccsketch.domain.user.dto.request.LoginRequestDto;
import com.example.ccsketch.domain.user.entity.User;
import com.example.ccsketch.domain.user.dto.request.SignUpRequestDto;
import com.example.ccsketch.domain.user.entity.UserGoal;
import com.example.ccsketch.domain.user.repository.UserGoalRepository;
import com.example.ccsketch.domain.user.repository.UserRepository;
import com.example.ccsketch.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserGoalRepository userGoalRepository;

    @Transactional
    public Long signUp(SignUpRequestDto requestDto) {
        // 로그인 ID 중복 확인
        if (userRepository.findByLoginId(requestDto.getLoginId()).isPresent()) { // findByEmail을 findByLoginId로 변경
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = User.builder()
                .loginId(requestDto.getLoginId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .birth(requestDto.getBirth())
                .location(Integer.valueOf(requestDto.getLocation()))
                .organizationStatus(requestDto.getOrganizationStatus())
                .financialStatus(requestDto.getFinancialStatus())
                .goal(requestDto.getGoal())
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return jwtTokenProvider.createToken(user.getLoginId());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String loginId = authentication.getName(); // JWT 토큰에서 loginId를 가져옴
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 현재 로그인한 유저 ID 가져오기 (Spring Security 기준)
    private Long getCurrentUserId() {
        // SecurityContext에서 유저 정보 가져오기
        return getCurrentUser().getUserId();
    }

    // 현재 로그인한 유저의 Goal 리스트 가져오기
    public List<String> getCurrentUserGoals() {
        Long userId = getCurrentUserId();
        List<UserGoal> goals = userGoalRepository.findByUser_UserId(userId);

        // UserGoal 엔티티의 goalName 필드를 String 리스트로 변환
        return goals.stream()
                .map(UserGoal::getGoalName)
                .collect(Collectors.toList());
    }

    public int getUserLocation() {
        return getCurrentUser().getLocation();
    }

}
