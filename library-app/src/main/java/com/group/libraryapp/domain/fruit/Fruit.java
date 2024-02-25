package com.group.libraryapp.domain.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;

import java.time.LocalDate;

public class Fruit {
    private String name;
    private LocalDate warehousingDate;
    private long price;
    private boolean isSold;

    public Fruit() {
    }

    public Fruit(FruitCreateRequest request) {
        this.name = request.getName();
        this.warehousingDate = request.getWarehousingDate();
        this.price = request.getPrice();
        this.isSold = false;
    }

    public void sellFruit() {
        isSold = true;
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

    public boolean isSold() {
        return isSold;
    }
}
