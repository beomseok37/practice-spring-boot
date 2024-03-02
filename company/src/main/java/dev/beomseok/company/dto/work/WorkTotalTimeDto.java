package dev.beomseok.company.dto.work;

import lombok.Getter;

@Getter
public class WorkTotalTimeDto {
    private Long id;
    private String name;
    private Long totalTime;

    public WorkTotalTimeDto(Long id, String name, Long totalTime) {
        this.id = id;
        this.name = name;
        this.totalTime = totalTime;
    }
}
