package com.group.libraryapp.dto.fruit;

import com.group.libraryapp.domain.fruit.Fruit;

import java.time.LocalDate;

public class FruitResponse {
    private String name;
    private LocalDate warehousingDate;
    private long price;

    public FruitResponse(Fruit fruit) {
        this.name = fruit.getName();
        this.warehousingDate = fruit.getWarehousingDate();
        this.price = fruit.getPrice();
    }

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
