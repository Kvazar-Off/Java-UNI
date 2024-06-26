<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<jsp:useBean id="mybean" scope="session" class="com.example.beans_jsp.MainBean"/>
<header>
    <nav>
        <li><a href="StartPage.jsp">Стартовая страница</a></li>
        <li><a href="MainPage.jsp">Главная страница</a></li>
        <li><a href="FinishPage.jsp">Финишная страница</a></li>
    </nav>
</header>
<main>
    <h1>Введите входные параметры для функции: </h1>
    <form name="Input form" action="MainPage.jsp">
        <label>
            <input type="text" name="array"/>
        </label>
        <input type="submit" value="Подтвердить" name="button1"/>
    </form>
    <form name="Input Form 2" action="FinishPage.jsp">
        <input type="submit" value="OK" name="button2"/>
    </form>
</main>
<h3>Счетчик на главной странице: ${mybean.countPageRefresh}</h3>
<%
    mybean.setCountPageRefresh(mybean.getCountPageRefresh() + 1);
    String args = request.getParameter("array");
    if (args != null) {
        mybean.computeFunction(args);
    }

%>
<h3>Тригер на главной странице: ${mybean.trigger}</h3>
<%
    mybean.setTrigger(!mybean.isTrigger());
%>
</body>
</html>
