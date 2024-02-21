package com.group.libraryapp.problem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int tryCount = inputTryCount();

        int[] randomNumberCount = getRandomNumberCount(tryCount);

        printRandomNumberCount(randomNumberCount);
    }

    private static int inputTryCount(){
        System.out.println("숫자를 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static int[] getRandomNumberCount(int tryCount){
        int[] randomNumberCount = new int[6];

        for(int i=0;i<tryCount;i++){
            int randomNumber = (int) (Math.random() * 6);
            randomNumberCount[randomNumber]++;
        }

        return randomNumberCount;
    }

    private static void printRandomNumberCount(int[] randomNumberCount){
        for(int i=0;i<6;i++){
            System.out.println((i+1)+"은 "+ randomNumberCount[i]+"번 나왔습니다.");
        }
    }
}
