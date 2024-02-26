package com.group.libraryapp.dto.fruit;

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

    public static FruitStatResponse createFruitStatMemory(List<FruitJdbc> filteredFruitJdbcs) {
        FruitStatResponse fruitStatResponse = new FruitStatResponse(0, 0);

        filteredFruitJdbcs.stream().forEach((fruitJdbc -> {
            if (fruitJdbc.isSold()) {
                fruitStatResponse.addSalesAmount(fruitJdbc.getPrice());
            } else {
                fruitStatResponse.addNotSalesAmount(fruitJdbc.getPrice());
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
