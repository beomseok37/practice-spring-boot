package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FruitMemoryRepository implements FruitRepository {

    List<Fruit> fruits = new ArrayList<>();


    @Override
    public void registerFruit(FruitCreateRequest request) {
        fruits.add(new Fruit(request));
    }

    @Override
    public void updateFruit(Long fruitId) {
        fruits.stream().forEach(fruit -> {
            if(fruitId==fruit.getId()){
                fruit.sellFruit();
            }
        });
    }

    @Override
    public List<Fruit> findPriceInfoByName(String fruitName) {
        return fruits.stream()
                .filter(fruit -> fruit.getName().equals(fruitName))
                .collect(Collectors.toList());
    }
}
