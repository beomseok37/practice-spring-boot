package com.group.libraryapp.dto.fruit;

import com.group.libraryapp.domain.fruit.Fruit;

import java.util.List;

public class FruitStatResponse {
    private long salesAmount;
    private long notSalesAmount;

    public FruitStatResponse(long salesAmount, long notSalesAmount) {
        this.salesAmount = salesAmount;
        this.notSalesAmount = notSalesAmount;
    }


    private void addSalesAmount(long salesAmount) {
        this.salesAmount += salesAmount;
    }

    private void addNotSalesAmount(long notSalesAmount) {
        this.notSalesAmount += notSalesAmount;
    }

    public static FruitStatResponse createFruitStatMemory(List<Fruit> filteredFruits) {
        FruitStatResponse fruitStatResponse = new FruitStatResponse(0, 0);

        filteredFruits.stream().forEach((fruit -> {
            if (fruit.isSold()) {
                fruitStatResponse.addSalesAmount(fruit.getPrice());
            } else {
                fruitStatResponse.addNotSalesAmount(fruit.getPrice());
            }
        }));

        return fruitStatResponse;
    }

    public static FruitStatResponse createFruitStatDB(List<FruitStatProjection> projectionResults) {
        FruitStatResponse fruitStatResponse = new FruitStatResponse(0, 0);

        projectionResults.forEach(pr -> {
            System.out.println("pr.getPrice()+pr.isIs_sold() = " + pr.getPrice() + pr.isIs_sold());
            if (pr.isIs_sold()) {
                fruitStatResponse.addSalesAmount(pr.getPrice());
            } else {
                fruitStatResponse.addNotSalesAmount(pr.getPrice());
            }
        });

        return fruitStatResponse;
    }

    public long getSalesAmount() {
        return salesAmount;
    }

    public long getNotSalesAmount() {
        return notSalesAmount;
    }
}
