package com.example.ccsketch.api.service;

import org.springframework.stereotype.Service;

import com.example.ccsketch.api.dto.CardDto;
import com.example.ccsketch.api.dto.PageResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    public PageResponseDto<CardDto> getCards(int page, int size) {
        List<CardDto> cards = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CardDto card = new CardDto();
            card.setCardId((long) (page * size + i + 1));
            card.setTitle("샘플 추천 카드 " + (page * size + i + 1));
            card.setSummary("설명 텍스트");
            card.setTags(List.of("샘플", "추천"));
            card.setDeadline(LocalDateTime.now().plusDays(30));
            cards.add(card);
        }

        PageResponseDto.PageInfo pageInfo = new PageResponseDto.PageInfo(page, size, 100L, (int) Math.ceil(100.0 / size));
        return new PageResponseDto<>(cards, pageInfo);
    }
}
