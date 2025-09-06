package com.example.ccsketch.domain.crawling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class noticeResponseDto {
    private int notice_id;
    private String notice_url;
    private String notice_title;
    private String notice_content;
}
