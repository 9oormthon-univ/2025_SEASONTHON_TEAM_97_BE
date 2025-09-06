package com.example.ccsketch.domain.crawling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 JSON 응답에서 제외
public class NoticeResponseDto {
    private Integer noticeId;
    private String noticeUrl;
    private String noticeTitle;
    private LocalDate noticeDateStart;
    private LocalDate noticeDateEnd;
    private String noticeSummary;
    private String noticeContent;
    private Integer noticeMinAge;
    private Integer noticeMaxAge;
    private String noticeOtherQual;
    private String noticeRegion;
    private String noticeAiSummary;
    private LocalDate noticeCreatedAt;
    private LocalDate noticeUpdatedAt;
}
