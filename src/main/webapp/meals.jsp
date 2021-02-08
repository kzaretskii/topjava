<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <style type="text/css">
        TABLE {
            border-collapse: collapse;
        }

        TD, TH {
            padding: 3px;
            border: 2px solid black;
        }

        .greenText {
            color: green;
        }

        .redText {
            color: red;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${requestScope.ListMealTo}" var="mealTo">
        <c:choose>
            <c:when test="${mealTo.excess}">
                <c:set var="textStyle" value="redText"/>
            </c:when>
            <c:otherwise>
                <c:set var="textStyle" value="greenText"/>
            </c:otherwise>
        </c:choose>
        <tr class="${textStyle}">
            <javatime:parseLocalDate value="${mealTo.date}" pattern="yyyy-MM-dd" var="parsedDate"/>
            <javatime:parseLocalTime value="${mealTo.time}" pattern="HH:mm" var="parsedTime"/>
            <td><c:out value="${parsedDate} ${parsedTime}"/></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>