package com.example.ccsketch.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponseDto {
    
    @Schema(description = "에러 코드", example = "INVALID_PARAMETER")
    private String errorCode;
    
    @Schema(description = "에러 상세 메시지")
    private String message;
    
    // 유효성 검사 실패 시, 어떤 필드가 잘못되었는지 담는 필드
    // private Map<String, String> fieldErrors; 


    public ErrorResponseDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}