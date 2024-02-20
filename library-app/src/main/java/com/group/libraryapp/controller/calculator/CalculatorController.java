package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.CalculatorAllRequest;
import com.group.libraryapp.dto.calculator.CalculatorAllResponse;
import com.group.libraryapp.dto.calculator.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    @GetMapping("/v1/multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }

    @GetMapping("/api/v1/calc")
    public CalculatorAllResponse calcByRequestParam(@RequestParam int num1, @RequestParam int num2){
        return new CalculatorAllResponse(num1+num2,num1-num2,num1*num2);
    }

    @GetMapping("/api/v2/calc")
    public CalculatorAllResponse calcByDto(CalculatorAllRequest request){
        return new CalculatorAllResponse(request.getNum1()+request.getNum2(),request.getNum1()-request.getNum2(),request.getNum1()*request.getNum2());
    }

    @GetMapping("/api/v1/day-of-the-week")
    public Map<String,String> getDayOfTheWeek(@RequestParam String date){
        LocalDate today = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        Map<String,String> result = new HashMap<>();
        result.put("dayOfTheWeek",today.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        return result;
    }

    @GetMapping("/api/v1/add-all")
    public int addAll(@RequestBody Map<String, List<Integer>> request){
        return request.get("numbers").stream().mapToInt(Integer::intValue).sum();
    }
}
