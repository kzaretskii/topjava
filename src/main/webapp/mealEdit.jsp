<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="mealTo" scope="request" type="ru.javawebinar.topjava.model.MealTo"/>
<html lang="ru">
<head>
    <style>
        input[type=datetime-local]
    </style>
    <title>Meal</title>
</head>
<body>
<h3><a href="./index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="post">
    <div>
        <input hidden name="id" value="${mealTo.id}">
        <label>DateTime:</label>
        <input type="datetime-local" name="datetime" value="${mealTo.dateTime}">
        <br>
        <label>Description:</label>
        <input type="text" name="description" value="${mealTo.description}">
        <br>
        <label>Calories:</label>
        <input type="number" min="1" name="calories" value="${mealTo.calories}">
        <br>
        <button type="submit" name="save" value="save">Save</button>
        <button type="submit" name="cancel" value="cancel">Cancel</button>
    </div>
</form>
<button onclick="document.location='/topjava/meals'">Cancel</button>
</body>
</html>