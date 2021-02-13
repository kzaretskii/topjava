package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoMemory implements MealDao {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private int count = 0;

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        int id;
        synchronized (this) {
            count++;
            id = count;
        }
        meals.put(id, new Meal(dateTime, description, calories, id));
    }

    @Override
    public void delete(int mealId) {
        meals.remove(mealId);
    }

    @Override
    public void updateMeal(LocalDateTime dateTime, String description, int calories, int id) {
        Meal meal = meals.get(id);
        if (meal == null)
            throw new IllegalArgumentException("Not found meal by id " + id);
        meals.put(id, new Meal(dateTime, description, calories, id));
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>();
        mealList.addAll(meals.values());
        return mealList;
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }
}
