package com.group.libraryapp.controller.fruit;

import com.group.libraryapp.dto.fruit.*;
import com.group.libraryapp.service.fruit.FruitServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FruitController {

    private final FruitServiceV2 fruitServiceV2;

    public FruitController(FruitServiceV2 fruitServiceV2) {
        this.fruitServiceV2 = fruitServiceV2;
    }

    @PostMapping("/api/v1/fruit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFruit(@RequestBody FruitCreateRequest request) {
        fruitServiceV2.registerFruit(request);
    }

    @PutMapping("/api/v1/fruit")
    public void sellFruit(@RequestBody Map<String, Long> request) {
        if (!request.containsKey("id")) {
            throw new IllegalArgumentException("옳바르지 않은 data 형식입니다.");
        }
        fruitServiceV2.sellFruit(request.get("id"));
    }

    @GetMapping("/api/v1/fruit/stat")
    public FruitStatResponse getFruitStat(@RequestParam String name) {
        return fruitServiceV2.getFruitByName(name);
    }

    @GetMapping("/api/v1/fruit/count")
    public FruitCountResponse getFruitCount(@RequestParam String name) {
        return new FruitCountResponse(fruitServiceV2.getFruitCount(name));
    }

    @GetMapping("/api/v1/fruit/list")
    public List<FruitResponse> getFruitList(@ModelAttribute FruitSearch fruitSearch) {
        return fruitServiceV2.getFruitList(fruitSearch);
    }
}
