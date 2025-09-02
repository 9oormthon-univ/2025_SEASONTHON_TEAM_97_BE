package com.example.ccsketch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ccsketch.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId); // 이 메소드로 변경 또는 추가
}