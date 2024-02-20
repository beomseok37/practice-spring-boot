package com.group.libraryapp.dto.calculator;

public class CalculatorAllRequest {
    int num1;
    int num2;

    public CalculatorAllRequest(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }
}
