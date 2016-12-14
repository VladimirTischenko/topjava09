<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>

    <style>
        .green {
            color: green; /* Цвет текста */
        }
        .red {
            color: red; /* Цвет текста */
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
    <table border="1">
        <tr>
            <th>DateTime</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan=2>Action</th>
        </tr>
        <jsp:useBean id="mealWithExceedList" scope="request" type="java.util.List"/>
        <c:forEach var="mealWithExceed" items="${mealWithExceedList}">
            <jsp:useBean id="mealWithExceed" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${mealWithExceed.exceed ? "red" : "green"}">
                <td>${mealWithExceed.date} ${mealWithExceed.time}</td>
                <td>${mealWithExceed.description}</td>
                <td>${mealWithExceed.calories}</td>
                <td><a href="meals?action=edit&mealId=${mealWithExceed.id}">Edit</a></td>
                <td><a href="meals?action=delete&mealId=${mealWithExceed.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

<h3>Add a Meal</h3>
<form method="POST" action='meals' name="frmAddUser">
    DateTime : <input type="datetime-local" name="DateTime"/> <br />
    Description : <input type="text" name="Description"/> <br />
    Calories : <input type="number" name="Calories"/> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
