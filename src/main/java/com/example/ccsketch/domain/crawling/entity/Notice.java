package com.example.ccsketch.domain.crawling.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Notice")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    @Column(name="notice_url", nullable = false, columnDefinition = "TEXT")
    private String noticeUrl;

    @Column(name="notice_title", nullable = false, columnDefinition = "TEXT")
    private String noticeTitle;

    @Column(name = "notice_date_start")
    private LocalDate noticeDateStart;
    @Column(name = "notice_date_end")
    private LocalDate noticeDateEnd;

    @Column(name="notice_summary", nullable = false, columnDefinition = "TEXT")
    private String noticeSummary;

    @Column(name="notice_content", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String noticeContent;

    @Column(name="notice_min_age", nullable = false)
    private Integer noticeMinAge;

    @Column(name="notice_max_age",nullable = false)
    private Integer noticeMaxAge;

    @Column(name="notice_other_qual")
    private String noticeOtherQual;
    @Column(name="notice_region")
    private String noticeRegion;
    @Column(name="notice_ai_summary")
    private String noticeAiSummary;

    @Column(name="notice_create_at", nullable = false)
    private LocalDate noticeCreatedAt;

    @Column(name="notice_update_at", nullable = false)
    private LocalDate noticeUpdatedAt;
}
