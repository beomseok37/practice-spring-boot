package dev.beomseok.company.dto.work;

import lombok.Getter;

@Getter
public class WorkOverTimeInfo {
    private Long id;
    private String name;
    private Long overTimeMinutes;

    public WorkOverTimeInfo(WorkTotalTimeDto totalWorkTime, Long baseOverTime) {
        this.id = totalWorkTime.getId();
        this.name = totalWorkTime.getName();
        this.overTimeMinutes = totalWorkTime.getTotalTime()>baseOverTime ? totalWorkTime.getTotalTime()-baseOverTime : 0;
    }
}
