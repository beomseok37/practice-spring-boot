package dev.beomseok.company.dto;

import lombok.Getter;

@Getter
public class OverTimeWorkInfo {
    private Long id;
    private String name;
    private Long overTimeMinutes;

    public OverTimeWorkInfo(TotalWorkTimeDto totalWorkTime,Long baseOverTime) {
        this.id = totalWorkTime.getId();
        this.name = totalWorkTime.getName();
        this.overTimeMinutes = totalWorkTime.getTotalTime()>baseOverTime ? totalWorkTime.getTotalTime()-baseOverTime : 0;
    }
}
