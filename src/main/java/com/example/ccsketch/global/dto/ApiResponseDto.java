package com.example.ccsketch.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiResponseDto<T> {

    @Schema(description = "응답 코드", example = "SUCCESS")
    private String code;

    @Schema(description = "응답 메시지", example = "성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "실제 응답 데이터")
    private T data;

    // 성공 시 사용하는 정적 메소드
    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>("SUCCESS", "성공적으로 처리되었습니다.", data);
    }

    // 실패 시 사용하는 정적 메소드
    public static <T> ApiResponseDto<T> fail(String code, String message) {
        return new ApiResponseDto<>(code, message, null);
    }
    
    // 생성자
    public ApiResponseDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public ApiResponseDto() {}
    
    // Getters and Setters
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}