package com.group.libraryapp.dto.fruit;

public class FruitSearch {
    private String option;
    private long price;

    public boolean isGTE() {
        if (option.equals("GTE")) {
            return true;
        } else if (option.equals("LTE")) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getOption() {
        return option;
    }

    public long getPrice() {
        return price;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
