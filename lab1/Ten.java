//package org.example;

import java.util.ArrayList;

public class Ten {
    public static void main(String[] args) {
        ArrayList<Integer> evenPositiveNumbers = new ArrayList<>();
        ArrayList<Integer> oddPositiveNumbers = new ArrayList<>();

        for (String arg : args) {
            int num = Integer.parseInt(arg);
            if (num > 0) {
                if (num % 2 == 0) {
                    evenPositiveNumbers.add(num);
                } else {
                    oddPositiveNumbers.add(num);
                }
            }
        }

        System.out.println("Чётные и положительные числа: " + evenPositiveNumbers);
        System.out.println("Нечётные и положительные числа: " + oddPositiveNumbers);

        int evenPositiveCount = evenPositiveNumbers.size();
        int oddPositiveCount = oddPositiveNumbers.size();

        if (evenPositiveCount > oddPositiveCount) {
            System.out.println("Больше чётных и положительных чисел");
        } else if (oddPositiveCount > evenPositiveCount) {
            System.out.println("Больше нечётных и положительных чисел");
        } else {
            System.out.println("Чётных и положительных чисел поровну с нечётными");
        }
    }
}