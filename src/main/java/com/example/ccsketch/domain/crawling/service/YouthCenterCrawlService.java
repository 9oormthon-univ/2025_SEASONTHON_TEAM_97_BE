package com.example.ccsketch.domain.crawling.service;

import com.example.ccsketch.domain.crawling.dto.response.NoticeResponseDto;
import com.example.ccsketch.domain.crawling.entity.Notice;
import com.example.ccsketch.domain.crawling.repository.NoticeRepository;
import com.example.ccsketch.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YouthCenterCrawlService {

    @Value("${youth_sketch.api.key}")
    private String apiKey;

    private final UserService userService;
    private final NoticeRepository noticeRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void youthNotice() throws IOException, InterruptedException {
        // 1. 요청 Url
        String url = "https://www.youthcenter.go.kr/go/ythip/getPlcy";

        // 2. 현재 로그인한 유저 지역 (법정시군구코드)
        String userLocation = String.valueOf(userService.getUserLocation());

        // 3. 요청 파라미터 공통값
        int pageNum = 1;
        int pageSize = 10;
        String pageType = "2";  // 1(목록)/2(상세)
        String rtnType = "json";  // xml/json


        // 4. HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();

        // URL 생성
        String requestUrl = String.format("%s?apiKeyNm=%s&pageNum=%d&pageSize=%d&pageType=%s&rtnType=%s&plcyKywdNm=%s",
                url, apiKey, pageNum, pageSize, pageType, rtnType, userLocation);

        System.out.println("요청 URL: " + requestUrl);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(requestUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("요청 성공");

            // 1️⃣ JSON 파싱
            List<NoticeResponseDto> noticeList = objectMapper.readValue(
                    response.body(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, NoticeResponseDto.class)
            );

            // 2️⃣ DB 저장
            for (NoticeResponseDto dto : noticeList) {
                Notice notice = Notice.builder()
                        .noticeUrl(dto.getNoticeUrl())
                        .noticeTitle(dto.getNoticeTitle())
                        .noticeDateStart(dto.getNoticeDateStart())
                        .noticeDateEnd(dto.getNoticeDateEnd())
                        .noticeSummary(dto.getNoticeSummary())
                        .noticeContent(dto.getNoticeContent())
                        .noticeMinAge(dto.getNoticeMinAge())
                        .noticeMaxAge(dto.getNoticeMaxAge())
                        .noticeOtherQual(dto.getNoticeOtherQual())
                        .noticeRegion(dto.getNoticeRegion())
                        .noticeAiSummary(dto.getNoticeAiSummary())
                        .noticeCreatedAt(LocalDate.now())
                        .noticeUpdatedAt(LocalDate.now())
                        .build();

                noticeRepository.save(notice);
            }

        } else {
            System.out.println("요청 실패: " + response.statusCode());
        }
    }
}
