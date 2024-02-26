package com.group.libraryapp.dto.fruit;

public class FruitMemory extends FruitJdbc {
    private long id;

    private static int idCount = 1;

    public FruitMemory(FruitCreateRequest request) {
        super(request);
        this.id = idCount++;
    }

    public long getId() {
        return id;
    }
}
