package com.group.libraryapp.controller.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FruitController {
    List<Fruit> fruits = new ArrayList<>();

    @PostMapping("/api/v1/fruit")
    public void createFruit(@RequestBody FruitCreateRequest request){
        fruits.add(new Fruit(request));
    }

    @PutMapping("/api/v1/fruit")
    public void sellFruit(@RequestBody Map<String, Long> request){
        fruits.stream().forEach(fruit -> {
            if(request.get("id")==fruit.getId()){
                fruit.sellFruit();
            }
        });
    }

    @GetMapping("/api/v1/fruit/stat")
    public FruitStatResponse getFruitStat(@RequestParam String name){
        List<Fruit> filteredFruits = fruits.stream().filter(fruit -> fruit.getName().equals(name)).collect(Collectors.toList());
        return new FruitStatResponse(filteredFruits);
    }
}
