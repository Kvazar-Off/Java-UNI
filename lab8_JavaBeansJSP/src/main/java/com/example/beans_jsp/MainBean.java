package com.example.beans_jsp;

import java.util.ArrayList;

public class MainBean {

    private int countPageRefresh;
    private boolean trigger = false;
    private String result;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> argsList = new ArrayList<String>();

    public MainBean() {
    }

    public void computeFunction(String args) {
        argsList.add(args); // сохраняем аргументы в списке
        int numberOfPositiveOddNumbers = 0;
        int numberOfPositiveEvenNumbers = 0;

        String[] numbers = args.split(" ");
        for (String arg : numbers) {
            int number = Integer.parseInt(arg);
            if (number % 2 == 0 && number > 0) {
                ++numberOfPositiveEvenNumbers;
            } else if (number > 0) {
                ++numberOfPositiveOddNumbers;
            }
        }

        if (numberOfPositiveOddNumbers > numberOfPositiveEvenNumbers) {
            result = "Нечётных положительных чисел больше";
        } else if (numberOfPositiveOddNumbers < numberOfPositiveEvenNumbers) {
            result = "Чётных положительных чисел больше";
        } else {
            result = "Нечётных положительных и чётных положительных чисел поровну";
        }

        results.add(result); // сохраняем результат в списке
    }

    public ArrayList<String> getResults() {
        return results;
    }

    public ArrayList<String> getArgsList() {
        return argsList;
    }

    public int getCountPageRefresh() {
        return countPageRefresh;
    }

    public void setCountPageRefresh(int countPageRefresh) {
        this.countPageRefresh = countPageRefresh;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public String getResult() {
        return result;
    }
}
