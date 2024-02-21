package com.group.libraryapp.dto.fruit;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FruitCreateRequest {
    private String name;

    private LocalDate warehousingDate;
    private Long price;

    public String getName() {
        return name;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }

    public long getPrice() {
        return price;
    }
}
