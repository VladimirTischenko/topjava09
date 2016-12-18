<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>User list</h2>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Enabled</th>
            <th>Registered</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td> ${user.name} </td>
                <td> ${user.email} </td>
                <td> ${user.enabled} </td>
                <td> ${fn:formatDateTime(user.registered)}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
