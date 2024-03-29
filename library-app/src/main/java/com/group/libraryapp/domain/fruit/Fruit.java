package com.group.libraryapp.domain.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
