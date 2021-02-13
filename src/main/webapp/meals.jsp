<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<a href="meals/edit">Add Meal</a>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${requestScope.listMealTo}" var="mealTo">
        <tr class="${mealTo.excess ? "redText" : "greenText"}">
            <td><c:out value="${mealTo.date} ${mealTo.time}"/></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td> <a href="meals/edit?id=${mealTo.id}">Update</a> </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>