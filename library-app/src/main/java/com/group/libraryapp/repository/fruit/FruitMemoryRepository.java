package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitJdbc;
import com.group.libraryapp.dto.fruit.FruitMemory;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FruitMemoryRepository implements FruitRepository {

    List<FruitMemory> fruits = new ArrayList<>();


    @Override
    public void registerFruit(FruitCreateRequest request) {
        fruits.add(new FruitMemory(request));
    }

    @Override
    public void updateFruit(Long fruitId) {
        fruits.stream().forEach(fruit -> {
            if (fruitId == fruit.getId()) {
                fruit.sellFruit();
            }
        });
    }

    @Override
    public FruitStatResponse findPriceInfoByName(String fruitName) {
        List<FruitJdbc> filteredFruitJdbcs = fruits.stream()
                .filter(fruit -> fruit.getName().equals(fruitName))
                .collect(Collectors.toList());

        return FruitStatResponse.createFruitStatMemory(filteredFruitJdbcs);
    }

    @Override
    public boolean isFruitNotExist(long id) {
        return fruits.stream().anyMatch(fruit -> fruit.getId() == id);
    }

}
