package com.group.libraryapp.dto.fruit;

public class FruitCountResponse {
    private long count;

    public FruitCountResponse(Long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }
}
