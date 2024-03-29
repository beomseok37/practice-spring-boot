package dev.beomseok.company.dto.member;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberCreateRequest {
    private long teamId;
    private String memberName;
    private Boolean isManager;
    private LocalDate birthday;
}
