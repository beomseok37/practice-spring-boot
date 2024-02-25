package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitDTO;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FruitMemoryRepository implements FruitRepository {

    List<FruitDTO> fruits = new ArrayList<>();


    @Override
    public void registerFruit(FruitCreateRequest request) {
        fruits.add(new FruitDTO(request));
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
        List<Fruit> filteredFruits = fruits.stream()
                .filter(fruit -> fruit.getName().equals(fruitName))
                .collect(Collectors.toList());

        return FruitStatResponse.createFruitStatMemory(filteredFruits);
    }

    @Override
    public boolean isFruitNotExist(long id) {
        return fruits.stream().anyMatch(fruit -> fruit.getId() == id);
    }

}
