package dev.beomseok.company.dto.work;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkInfo {
    protected LocalDate date;
    protected long workingMinutes;

    public WorkInfo(LocalDate date, long workingMinutes){
        this.date= date;
        this.workingMinutes = workingMinutes;
    }
}
