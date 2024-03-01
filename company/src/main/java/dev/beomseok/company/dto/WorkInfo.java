package dev.beomseok.company.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkInfo {
    private LocalDate date;
    private long workingMinutes;

    public WorkInfo(LocalDate date, long workingMinutes){
        this.date= date;
        this.workingMinutes = workingMinutes;
    }
}
