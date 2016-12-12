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
            <td>DateTime</td>
            <td>Description</td>
            <td>Calories</td>
        </tr>
        <jsp:useBean id="mealWithExceedList" scope="request" type="java.util.List"/>
        <c:forEach var="mealWithExceed" items="${mealWithExceedList}">
            <jsp:useBean id="mealWithExceed" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <c:choose>
                <c:when test="${mealWithExceed.exceed == false}">
                    <tr class="green">
                        <td>${mealWithExceed.dateTime.toString().replace('T', ' ')}</td>
                        <td>${mealWithExceed.description}</td>
                        <td>${mealWithExceed.calories}</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr class="red">
                        <td>${mealWithExceed.dateTime.toString().replace('T', ' ')}</td>
                        <td>${mealWithExceed.description}</td>
                        <td>${mealWithExceed.calories}</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>
</body>
</html>
