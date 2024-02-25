package com.group.libraryapp.controller.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import com.group.libraryapp.service.fruit.FruitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping("/api/v1/fruit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFruit(@RequestBody FruitCreateRequest request) {
        fruitService.registerFruit(request);
    }

    @PutMapping("/api/v1/fruit")
    public void sellFruit(@RequestBody Map<String, Long> request) {
        if (!request.containsKey("id")) {
            throw new IllegalArgumentException("옳바르지 않은 data 형식입니다.");
        }
        fruitService.sellFruit(request.get("id"));
    }

    @GetMapping("/api/v1/fruit/stat")
    public FruitStatResponse getFruitStat(@RequestParam String name) {
        return fruitService.getFruitByName(name);
    }
}
