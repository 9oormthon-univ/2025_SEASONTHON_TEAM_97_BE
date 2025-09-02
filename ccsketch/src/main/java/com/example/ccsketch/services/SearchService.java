package com.example.ccsketch.services;

import org.springframework.stereotype.Service;

import com.example.ccsketch.DTO.CardDto;
import com.example.ccsketch.DTO.PageResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    public PageResponseDto<CardDto> searchByKeyword(String keyword, int page, int size) {
        List<CardDto> cards = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CardDto card = new CardDto();
            card.setCardId((long) (page * size + i + 1));
            card.setTitle((keyword != null && !keyword.isBlank() ? keyword + " " : "") + "검색 결과 카드 " + (page * size + i + 1));
            card.setSummary("검색 키워드 기반 예시 데이터");
            card.setTags(keyword == null || keyword.isBlank() ? List.of("검색", "예시") : List.of("검색", keyword));
            card.setDeadline(LocalDateTime.now().plusDays(20));
            cards.add(card);
        }

        long total = 80L;
        PageResponseDto.PageInfo pageInfo = new PageResponseDto.PageInfo(page, size, total, (int) Math.ceil(total / (double) size));
        return new PageResponseDto<>(cards, pageInfo);
    }
}



