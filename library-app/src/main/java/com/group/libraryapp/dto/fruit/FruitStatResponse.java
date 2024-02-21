package com.group.libraryapp.dto.fruit;

import com.group.libraryapp.domain.fruit.Fruit;

import java.util.List;

public class FruitStatResponse {
    private long salesAmount;
    private long notSalesAmount;

    public FruitStatResponse(List<Fruit> filteredFruits) {
        this.salesAmount = 0;
        this.notSalesAmount = 0;

        filteredFruits.stream().forEach((fruit -> {
            if(fruit.isSold()){
                salesAmount+=fruit.getPrice();
            } else {
                notSalesAmount+=fruit.getPrice();
            }
        }));
    }

    public long getSalesAmount() {
        return salesAmount;
    }

    public long getNotSalesAmount() {
        return notSalesAmount;
    }
}
