package com.group.libraryapp.service.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.repository.fruit.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;


    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public void registerFruit(FruitCreateRequest request) {
        fruitRepository.registerFruit(request);
    }

    public void sellFruit(Long fruitId) {
        fruitRepository.updateFruit(fruitId);
    }

    public List<Fruit> getFruitByName(String fruitName) {
        return fruitRepository.findPriceInfoByName(fruitName);
    }

}
