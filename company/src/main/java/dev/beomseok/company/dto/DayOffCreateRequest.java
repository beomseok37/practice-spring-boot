package dev.beomseok.company.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DayOffCreateRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
