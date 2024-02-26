package com.group.libraryapp.service.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import com.group.libraryapp.repository.fruit.FruitRepository;
import org.springframework.stereotype.Service;

@Service
public class FruitServiceV1 {

    private final FruitRepository fruitRepository;


    public FruitServiceV1(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public void registerFruit(FruitCreateRequest request) {
        fruitRepository.registerFruit(request);
    }

    public void sellFruit(Long fruitId) {
        fruitRepository.updateFruit(fruitId);
    }

    public FruitStatResponse getFruitByName(String fruitName) {
        return fruitRepository.findPriceInfoByName(fruitName);
    }

}
