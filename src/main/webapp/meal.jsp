<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 12.12.2016
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3><a href="meals?action=mealList">Back to Meal List</a></h3>
<h3>Edit a Meal</h3>
<form method="POST" action='meals' name="frmAddUser">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <input type="hidden" name="Id" value="<c:out value="${meal.id}" />" /> <br />
    DateTime : <input type="datetime-local" name="DateTime" value="<c:out value="${meal.dateTime}" />" /> <br />
    Description : <input type="text" name="Description" value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input type="number" name="Calories" value="<c:out value="${meal.calories}" />" /> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
