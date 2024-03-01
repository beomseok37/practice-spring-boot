package dev.beomseok.company.dto;

import lombok.Getter;

@Getter
public class MemberOverTimeInfo {
    private Long id;
    private String name;
    private int overTimeMinutes;
}
