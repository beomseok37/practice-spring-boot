package dev.beomseok.company.dto;

import lombok.Getter;

@Getter
public class TotalWorkTimeDto {
    private Long id;
    private String name;
    private Long totalTime;

    public TotalWorkTimeDto(Long id, String name, Long totalTime) {
        this.id = id;
        this.name = name;
        this.totalTime = totalTime;
    }
}
