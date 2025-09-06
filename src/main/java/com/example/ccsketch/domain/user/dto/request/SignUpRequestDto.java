package com.example.ccsketch.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class SignUpRequestDto {
    private String loginId;
    private String password;
    private String name;
    private LocalDate birth;
    private Integer location;
    private Integer organizationStatus;
    private Integer financialStatus;
    private Integer goal;
}