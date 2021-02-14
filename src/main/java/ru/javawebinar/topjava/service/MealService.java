package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        Meal result = repository.save(meal, userId);
        checkResult(result);
        return result;
    }

    public Meal get(int id, int userId) {
        Meal result = repository.get(id, userId);
        checkResult(result);
        return result;
    }

    public void update(Meal meal, int userId) {
        if (meal.isNew())
            throw new NotFoundException("Not found meal");
        Meal result = repository.save(meal, userId);
        checkResult(result);
    }

    public void delete(int id, int userId) {
        boolean result = repository.delete(id, userId);
        checkResult(result);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    private void checkResult(Meal meal) {
        if (meal == null)
            throw new NotFoundException("Not found meal");
    }

    private void checkResult(boolean result) {
        if (!result)
            throw new NotFoundException("Not found meal");
    }

}