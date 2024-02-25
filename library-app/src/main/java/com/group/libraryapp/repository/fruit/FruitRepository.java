package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitStatResponse;

public interface FruitRepository {
    void registerFruit(FruitCreateRequest request);

    void updateFruit(Long fruitId);

    FruitStatResponse findPriceInfoByName(String fruitName);

    boolean isFruitNotExist(long id);
}
