package com.example.ccsketch.domain.crawling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class YouthCenterCrawlService {

    @Value("${youth_sketch.api.key}")
    private String apiKey;

    // todo: 반환값 dto 생성하기
    public void youthNotice() throws IOException, InterruptedException {
        // 1. 요청 Url
        String url = "https://www.youthcenter.go.kr/go/ythip/getPlcy";

        // 2. 요청 파라미터
        int pageNum = 1;
        int pageSize = 10;
        String pageType = "2";  // 1(목록)/2(상세)
        String rtnType="json";  // xml/json
//            String plcyKywdNm = ""    // 정책키워드명(청년 등)
//            String zipCd = "";   // 법정시군구코드
//            String lclsfNm = "";  // 대분류명(일자리/교육/주거/금융/생활복지문화/참여)
//            String mclsfNm = "";  // 중분류명(취업/창업, 학비부담완화/고졸청년취업지원 등)

        // 3. 파라미터를 URL에 붙이기
        String requestUrl = String.format("%s?apiKeyNm=%s&pageNum=%d&pageSize=%d&pageType=%s&rtnType=%s",
                url, apiKey, pageNum, pageSize, pageType, rtnType);
        // java.net.URLEncoder.encoder(plcyKywdNm, "UTF-8"));

        System.out.printf(requestUrl);

        // 4. HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();

        // 5. GET 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(requestUrl))
                .GET()
                .build();

        // 6. 요청 보내기
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 7. 상태 코드 확인
        if(response.statusCode() == 200){
            System.out.println("요청 성공");
            System.out.println(response.body());
        } else{
            System.out.println("요청 실패: " + response.statusCode());
        }

    }

}
