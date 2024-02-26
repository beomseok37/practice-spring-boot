package com.group.libraryapp.dto.fruit;

public class FruitStatProjection {
    private long price;
    private boolean is_sold;

    public FruitStatProjection(long price, boolean is_sold) {
        this.price = price;
        this.is_sold = is_sold;
    }

    public long getPrice() {
        return price;
    }

    public boolean isIs_sold() {
        return is_sold;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setIs_sold(boolean is_sold) {
        this.is_sold = is_sold;
    }
}
