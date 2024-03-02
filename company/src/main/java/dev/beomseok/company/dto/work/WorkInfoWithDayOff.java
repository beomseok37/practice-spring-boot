package dev.beomseok.company.dto.work;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkInfoWithDayOff extends WorkInfo{

    private Boolean usingDayOff;

    public WorkInfoWithDayOff(LocalDate date, long workingMinutes, Boolean usingDayOff) {
        super(date, workingMinutes);
        this.usingDayOff = usingDayOff;
    }
}
