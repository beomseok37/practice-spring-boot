package com.group.libraryapp.dto.calculator;

public class CalculatorAllResponse {
    int addResult;
    int minusResult;
    int multiplyResult;

    public CalculatorAllResponse(int addResult, int minusResult, int multiplyResult) {
        this.addResult = addResult;
        this.minusResult = minusResult;
        this.multiplyResult = multiplyResult;
    }

    public int getAddResult() {
        return addResult;
    }

    public int getMinusResult() {
        return minusResult;
    }

    public int getMultiplyResult() {
        return multiplyResult;
    }
}
