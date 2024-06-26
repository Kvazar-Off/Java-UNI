package client;

import compute.Task;
import java.util.Collections;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Counter implements Task<List<Integer>>, Serializable {

    private String[] args;

    public Counter(String[] args) {
        this.args = args;
    }

    public List<Integer> execute() {
        ArrayList<Integer> al = new ArrayList<>();//создане списка для чётных чисел
        for (String x : args) {//Вывод всех заданных параметров командной строки
            /* преобразвание строки, хранящейся в переменной "х" в целое число и добавление в список */
            al.add((Integer.parseInt(x)));//добавление в список чётно
        }
        Collections.sort(al);
        return al;
    }

}
