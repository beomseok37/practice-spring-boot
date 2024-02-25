package com.group.libraryapp.dto.fruit;

import com.group.libraryapp.domain.fruit.Fruit;

public class FruitDTO extends Fruit {
    private long id;

    private static int idCount = 1;

    public FruitDTO(FruitCreateRequest request) {
        super(request);
        this.id = idCount++;
    }

    public long getId() {
        return id;
    }
}
