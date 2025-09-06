package com.example.ccsketch.domain.user.service;

import com.example.ccsketch.domain.user.entity.UserGoal;
import com.example.ccsketch.domain.user.repository.UserGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserGoalService {
    private final UserGoalRepository userGoalRepository;

    public List<String> getUserGoals(Long userId) {
        return userGoalRepository.findByUserId(userId)
                .stream()
                .map(UserGoal::getGoalName)
                .collect(Collectors.toList());
    }
}
