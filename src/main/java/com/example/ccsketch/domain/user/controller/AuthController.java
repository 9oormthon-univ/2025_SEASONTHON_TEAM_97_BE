package com.example.ccsketch.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ccsketch.domain.user.dto.request.SignUpRequestDto;
import com.example.ccsketch.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth API", description = "사용자 인증 관련 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController { // 클래스 분리 추천

    private final UserService userService;

//    @Operation(summary = "회원가입")
//    @PostMapping("/signup")
//    public ResponseEntity<ApiResponseDto<Long>> signUp(@RequestBody SignUpRequestDto requestDto) {
//        Long userId = userService.signUp(requestDto);
//        return ResponseEntity.ok(ApiResponseDto.success(userId));
//    }
}