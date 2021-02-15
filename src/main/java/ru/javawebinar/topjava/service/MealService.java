package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        Meal result = repository.save(meal, userId);
        return result;
    }

    public Meal get(int id, int userId) {
        Meal result = repository.get(id, userId);
        ValidationUtil.checkNotFoundWithId(result, id);
        return result;
    }

    public void update(Meal meal, int userId) {
        Meal result = repository.save(meal, userId);
        ValidationUtil.checkNotFoundWithId(result, meal.getId());
    }

    public void delete(int id, int userId) {
        boolean result = repository.delete(id, userId);
        ValidationUtil.checkNotFoundWithId(result, id);
    }

    public List<MealTo> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(userId), caloriesPerDay);
    }

    public List<MealTo> getAllWithFilter(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo, int userId, int caloriesPerDay) {
        return MealsUtil.getFilteredTos(repository.getAllFilter(dateFrom, dateTo, userId),
                caloriesPerDay, timeFrom, timeTo);
    }
}