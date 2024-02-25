package com.group.libraryapp.controller.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import com.group.libraryapp.service.fruit.FruitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping("/api/v1/fruit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFruit(@RequestBody FruitCreateRequest request){
        fruitService.registerFruit(request);
    }

    @PutMapping("/api/v1/fruit")
    public void sellFruit(@RequestBody Map<String, Long> request){
        if(!request.containsKey("id")){
            throw new IllegalArgumentException("해당 id의 과일이 존재하지 않습니다.");
        }
        fruitService.sellFruit(request.get("id"));
    }

    @GetMapping("/api/v1/fruit/stat")
    public FruitStatResponse getFruitStat(@RequestParam String name){
        List<Fruit> filteredFruits = fruitService.getFruitByName(name);
        return new FruitStatResponse(filteredFruits);
    }
}
