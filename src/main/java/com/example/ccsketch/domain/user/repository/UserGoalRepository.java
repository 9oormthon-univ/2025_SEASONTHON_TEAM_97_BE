package com.example.ccsketch.domain.user.repository;

import com.example.ccsketch.domain.user.entity.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserGoalRepository extends JpaRepository<UserGoal, Long> {
    List<UserGoal> findByUserId(Long userId);
}
