package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {
    void add(LocalDateTime dateTime, String description, int calories);

    void delete(int mealId);

    void updateMeal(LocalDateTime dateTime, String description, int calories, int id);

    List<Meal> getAll();

    Meal getById(int id);
}
