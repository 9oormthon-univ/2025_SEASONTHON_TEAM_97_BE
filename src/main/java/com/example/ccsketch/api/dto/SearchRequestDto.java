package com.example.ccsketch.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SearchRequestDto {
    @Schema(description = "검색할 키워드", required = true, example = "청년 창업")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
