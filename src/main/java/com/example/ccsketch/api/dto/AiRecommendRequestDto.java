package com.example.ccsketch.api.dto;

public class AiRecommendRequestDto {
    private Long userId;
    private int size;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public AiRecommendRequestDto() {
    }

    public AiRecommendRequestDto(Long userId, int size) {
        this.userId = userId;
        this.size = size;
    }
}
