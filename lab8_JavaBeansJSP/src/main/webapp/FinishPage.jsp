<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Финишная страница</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #333;
            color: #fff;
            padding: 10px 0;
        }
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }
        nav ul li {
            display: inline;
            margin-right: 10px;
        }
        nav ul li a {
            color: #fff;
            text-decoration: none;
        }
        main {
            padding: 20px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="StartPage.jsp">Стартовая страница</a></li>
            <li><a href="MainPage.jsp">Главная страница</a></li>
            <li><a href="FinishPage.jsp">Финишная страница</a></li>
        </ul>
    </nav>
</header>
<main>
    <jsp:useBean id="mybean" scope="session" class="com.example.beans_jsp.MainBean"/>
    <jsp:setProperty name="mybean" property="*"/>
<!--    <h3>Результат: </h3>
    <p>${mybean.result}</p>-->
    <h3>Результаты вычислений:</h3>
    <table border="1">
  <thead>
    <tr>
      <th>Аргументы</th>
      <th>Результат</th>
    </tr>
  </thead>
  <tbody>
    <% for (int i = 0; i < mybean.getResults().size(); i++) { %>
      <tr>
        <td><%= mybean.getArgsList().get(i) %></td>
        <td><%= mybean.getResults().get(i) %></td>
      </tr>
    <% } %>
  </tbody>
</table>
</main>
</body>
</html>
