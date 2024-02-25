package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FruitRepository {
    void registerFruit(FruitCreateRequest request);

    void updateFruit(Long fruitId);

    List<Fruit> findPriceInfoByName(String fruitName);
}
