package com.example.ccsketch.services;
import com.example.ccsketch.domain.User;
import com.example.ccsketch.DTO.LoginRequestDto;
import com.example.ccsketch.util.JwtTokenProvider;
import com.example.ccsketch.DTO.SignUpRequestDto;
import com.example.ccsketch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
                .location(requestDto.getLocation())
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
}