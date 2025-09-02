package com.example.ccsketch.global.config;


import com.example.ccsketch.domain.user.entity.User;
import com.example.ccsketch.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder import
import org.springframework.stereotype.Component;

import java.time.LocalDate; // LocalDate import

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위해 주입

    @Override
    public void run(String... args) throws Exception {
        // Mock 데이터가 이미 있는지 확인 (loginId 기준)
        if (userRepository.findByLoginId("testuser").isEmpty()) {
            User mockUser = User.builder()
                    .loginId("testuser")
                    // 실제 DB에 저장될 때처럼 비밀번호를 암호화해서 넣어줍니다.
                    .password(passwordEncoder.encode("password123"))
                    .name("테스트유저")
                    .birth(LocalDate.of(2000, 1, 1))
                    .location("서울")
                    .organizationStatus("대학생")
                    .financialStatus(1)
                    .goal(1)
                    .build();
            userRepository.save(mockUser);
        }
    }
}