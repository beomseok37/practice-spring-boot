package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.CalculatorAddRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @GetMapping("/v1/add")
    public int addTwoNumbers1(CalculatorAddRequest request) {
        return request.getNumber1() + request.getNumber2();
    }

    @GetMapping("/v2/add")
    public int addTwoNumbers2(@RequestParam int number1, @RequestParam int number2) {
        return number1 + number2;
    }
}
