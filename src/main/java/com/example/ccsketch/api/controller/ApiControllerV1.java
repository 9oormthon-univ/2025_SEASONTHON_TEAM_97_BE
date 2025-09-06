package com.example.ccsketch.api.controller;

import com.example.ccsketch.global.dto.ApiResponseDto;
import com.example.ccsketch.api.dto.CardDto;
import com.example.ccsketch.api.dto.PageResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API의 V1 엔드포인트를 담당하는 메인 컨트롤러입니다.
 */
@Tag(name = "V1 Main API", description = "프로젝트의 핵심 API 모음")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiControllerV1 {

    // --- 의존성 주입 (Lombok의 @RequiredArgsConstructor를 통해 생성자 주입) ---
    // private final RecommendationService recommendationService;
    // private final SearchService searchService;
    // private final NotificationService notificationService;
    // private final PostService postService;


    // =================================================================
    // == Day 1: 맞춤 추천 및 검색 API
    // =================================================================

    @Operation(summary = "맞춤 추천 카드 목록 조회", description = "사용자에게 개인화된 추천 카드 목록을 페이지네이션하여 제공합니다.")
    @GetMapping("/recommendations/cards")
    public ResponseEntity<ApiResponseDto<PageResponseDto<CardDto>>> getRecommendationCards(
            @Parameter(description = "페이지 번호 (0부터 시작)", required = true, example = "0") @RequestParam int page,
            @Parameter(description = "페이지 당 데이터 수", required = true, example = "10") @RequestParam int size
    ) {
        // TODO: 서비스 레이어를 호출하여 추천 카드 데이터를 조회하는 로직 구현
        // PageResponseDto<CardDto> responseData = recommendationService.getRecommendedCardsForUser(page, size);
        PageResponseDto<CardDto> mockData = null; // 임시 데이터

        return ResponseEntity.ok(ApiResponseDto.success(mockData));
    }

    @Operation(summary = "키워드 기반 카드 검색", description = "주어진 키워드로 카드를 검색하고, 결과를 페이지네이션하여 제공합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<PageResponseDto<CardDto>>> searchCards(
            @Parameter(description = "검색 키워드", required = true, example = "정부지원") @RequestParam String keyword,
            @Parameter(description = "페이지 번호 (0부터 시작)", required = true, example = "0") @RequestParam int page,
            @Parameter(description = "페이지 당 데이터 수", required = true, example = "10") @RequestParam int size
    ) {
        // TODO: 서비스 레이어를 호출하여 키워드 검색 로직 구현
        // PageResponseDto<CardDto> responseData = searchService.searchByKeyword(keyword, page, size);
        PageResponseDto<CardDto> mockData = null; // 임시 데이터

        return ResponseEntity.ok(ApiResponseDto.success(mockData));
    }


    // =================================================================
    // == Day 2: 자동화 및 부가 핵심 기능 API
    // =================================================================

    @Operation(summary = "마감일 알림 등록", description = "사용자가 특정 카드의 마감일에 대해 알림을 받도록 등록합니다.")
    @PostMapping("/notifications/schedule")
    public ResponseEntity<ApiResponseDto<Object>> scheduleNotification(
            // @RequestBody NotificationRequestDto notificationRequestDto
    ) {
        // TODO: 서비스 레이어를 호출하여 알림 스케줄링 로직 구현
        // NotificationResponseDto responseData = notificationService.schedule(notificationRequestDto);
        Object mockData = null; // 임시 데이터

        return ResponseEntity.ok(ApiResponseDto.success(mockData));
    }


    // =================================================================
    // == Day 3+: 커뮤니티, 즐겨찾기 등 확장 기능 (예시)
    // =================================================================

    @Operation(summary = "게시글 상세 조회", description = "ID를 사용하여 특정 게시글의 상세 정보를 조회합니다.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto<Object>> getPostById(
            @Parameter(description = "조회할 게시글의 ID", required = true, example = "123") @PathVariable Long postId
    ) {
        // TODO: 서비스 레이어를 호출하여 게시글을 조회하는 로직 구현
        // PostDetailDto responseData = postService.getPostDetails(postId);
        Object mockData = null; // 임시 데이터

        return ResponseEntity.ok(ApiResponseDto.success(mockData));
    }
}
