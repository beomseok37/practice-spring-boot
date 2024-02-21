package com.group.libraryapp.domain.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fruit {
    private Long id;
    private String name;
    private LocalDate warehousingDate;
    private long price;
    private boolean isSold;

    private static Long idCount = 1L;

    public Fruit(FruitCreateRequest request) {
        this.id = idCount++;
        this.name = request.getName();
        this.warehousingDate = request.getWarehousingDate();
        this.price = request.getPrice();
        this.isSold = false;
    }

    public void sellFruit(){
        isSold=true;
    }

    public Long getId() {
        return id;
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
