package com.example.ccsketch.domain.user.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 활성화
@Table(name = "Users") // H2 예약어 충돌 방지를 위해 복수형 사용
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // DB 컬럼명과 필드명을 매핑
    private Long userId;

    @Column(name = "login_id", nullable = false, unique = true, length = 64)
    private String loginId;

    @Column(nullable = false, length = 255) // 비밀번호는 해싱되면 길어지므로 넉넉하게
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    private LocalDate birth; // DB의 DATE 타입과 매핑

    @Column(length = 100)
    private String location;

    @Column(name = "organization_status", length = 50)
    private String organizationStatus; // 컬럼명이 길어서 잘렸지만, 전체 이름을 사용

    @Column(name = "financial_status")
    private Integer financialStatus;

    private Integer goal;

    @CreatedDate // 엔티티 생성 시 자동으로 현재 시간 저장
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(String loginId, String password, String name, LocalDate birth, String location, String organizationStatus, Integer financialStatus, Integer goal) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.location = location;
        this.organizationStatus = organizationStatus;
        this.financialStatus = financialStatus;
        this.goal = goal;
    }
}