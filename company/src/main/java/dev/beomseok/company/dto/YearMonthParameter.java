package dev.beomseok.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class YearMonthParameter {
    private int year;
    private int month;

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.of(year,month,1,0,0);
    }
}
