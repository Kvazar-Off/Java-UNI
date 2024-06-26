package com.example.myservlet;

import java.io.*;
import java.util.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private static List<Map.Entry<String, String>> storage;
    private static final byte min_size = 1;
    private static final byte default_size = 5;
    public static byte size;
    public static long counter;
    public static boolean b;
    public static String ast;

    public HelloServlet() {
        HelloServlet.ast = "a static var c=";
        storage = new ArrayList<>();
        size = default_size;
        HelloServlet.counter = 0;
        HelloServlet.b = false;
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
            String fio = request.getParameter("fio");
            String group = request.getParameter("group");
            out.println(fio + " + " + group);

            HelloServlet.b = !HelloServlet.b;
            out.println("<h3> Servlet1.ast + Servlet1.b :" + HelloServlet.ast + HelloServlet.b + "</h3>");

            String toSize = request.getParameter("size");
            if (toSize != null && Integer.parseInt(toSize) > 0 && Integer.parseInt(toSize) < 6) {
                size = Byte.parseByte(toSize);
            }

            String args = request.getParameter("args");
            if (args != null) {
                storage.add(new AbstractMap.SimpleEntry<>(args, sort_func(args.split(" "))));
            }

            for (Map.Entry<String, String> elem : storage) {
                out.println(elem.getKey() + " " + elem.getValue());
                out.println("<p></p>");
            }
            out.println("<h" + HelloServlet.size + "></table>");

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
        ArrayList<Integer> al = new ArrayList<>();//создане списка для чётных чисел
        for (String x : args) {//Вывод всех заданных параметров командной строки
            /* преобразвание строки, хранящейся в переменной "х" в целое число и добавление в список */
            al.add((Integer.parseInt(x)));//добавление в список чётно
        }
        Collections.sort(al);
        return String.valueOf(al);
    }
}
