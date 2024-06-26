package com.myservlet.lab7;

import java.io.*;
import java.util.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/my-servlet")
public class MyServlet extends HttpServlet {
    private static List<Map.Entry<String, String>> storage;
    private static final byte min_size = 8;
    private static final byte default_size = 16;
    public static byte size;
    public static long counter;

    public MyServlet() {
        storage = new ArrayList<>();
        size = default_size;
        MyServlet.counter = 0;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Trifonov Sergei Alekseevich 4318 </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>ServletAppl" + request.getServletPath() + "</h1>");
            
            // Отображение ФИО студента и номера группы
            String fio = request.getParameter("fio");
            String group = request.getParameter("group");
            if (fio != null && group != null) {
                out.println("<p>Студент: " + fio + "</p>");
                out.println("<p>Группа: " + group + "</p>");
            }
          
            // Увеличение счетчика при обращении к странице
            MyServlet.counter++;
            out.println("<p>Количество обращений к странице: " + MyServlet.counter + "</p>");

            // Изменение размера текста в таблице
            String toSize = request.getParameter("size");
            if (toSize != null && Integer.parseInt(toSize) > 0 && Integer.parseInt(toSize) < (default_size + 1)) {
                size = Byte.parseByte(toSize);
            }

            String args = request.getParameter("args");
            if (args != null) {
                storage.add(new AbstractMap.SimpleEntry<>(args, sort_func(args.split(" "))));
            }

            for (Map.Entry<String, String> elem : storage) {
                out.println("<p style='font-size: " + size + "px;'>" + elem.getKey() + " " + elem.getValue() + "</p>");
            }


            if (size == min_size) {
                out.println("<h3> Достигунт лимит шрифта.</h3>");
            } else {
                size--;
            }
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String sort_func(String[] args) {
        int numberOfPositiveOddNumbers = 0;
        int numberOfPositiveEvenNumbers = 0;

        for (String arg : args) {
            int number = Integer.parseInt(arg);
            if (number % 2 == 0 && number > 0) {
                ++numberOfPositiveEvenNumbers;
            } else if (number > 0) {
                ++numberOfPositiveOddNumbers;
            }
        }


        String result = "";
        if (numberOfPositiveOddNumbers > numberOfPositiveEvenNumbers) {
            result = "Нечётных положительных чисел больше";
        } else if (numberOfPositiveOddNumbers < numberOfPositiveEvenNumbers) {
            result = "Чётных положительных чисел больше";
        } else {
            result = "Нечётных положительных и чётных положительных чисел поровну";
        }

        return result;
    }
}
