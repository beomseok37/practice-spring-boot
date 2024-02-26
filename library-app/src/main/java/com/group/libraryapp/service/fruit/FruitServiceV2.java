package com.group.libraryapp.service.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.*;
import com.group.libraryapp.repository.fruit.FruitJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FruitServiceV2 {

    private final FruitJpaRepository fruitJpaRepository;


    public FruitServiceV2(FruitJpaRepository fruitJpaRepository) {
        this.fruitJpaRepository = fruitJpaRepository;
    }

    @Transactional
    public void registerFruit(FruitCreateRequest request) {

        fruitJpaRepository.save(new Fruit(request));
    }

    @Transactional
    public void sellFruit(Long fruitId) {
        Fruit fruit = fruitJpaRepository.findById(fruitId).orElseThrow(IllegalArgumentException::new);
        fruit.sellFruit();
    }

    public FruitStatResponse getFruitByName(String fruitName) {
        List<FruitStatProjection> result = fruitJpaRepository.findByIsSold();
        return FruitStatResponse.createFruitStatDB(result);
    }

    public Long getFruitCount(String name) {
        return fruitJpaRepository.countByName(name);
    }

    public List<FruitResponse> getFruitList(FruitSearch fruitSearch) {
        List<Fruit> result = fruitSearch.isGTE() ?
                fruitJpaRepository.findByPriceGreaterThanEqual(fruitSearch.getPrice()) :
                fruitJpaRepository.findByPriceLessThanEqual(fruitSearch.getPrice());

        return result.stream().map(FruitResponse::new).collect(Collectors.toList());
    }
}
