package com.example.ccsketch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public class CardDto {

    @Schema(description = "카드의 고유 ID", example = "1")
    private Long cardId;

    @Schema(description = "카드 제목", example = "정부 지원 청년 창업 자금")
    private String title;

    @Schema(description = "카드 요약 내용", example = "IT 분야 유망 스타트업을 위한 초기 자금을 지원합니다.")
    private String summary;

    @Schema(description = "관련 태그 목록", example = "[\"IT\", \"정부지원\", \"청년\"]")
    private List<String> tags;

    @Schema(description = "마감일", example = "2025-10-15T23:59:59")
    private LocalDateTime deadline;
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

}
