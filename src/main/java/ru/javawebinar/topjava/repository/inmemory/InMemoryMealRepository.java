package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getDescription().contains("user1") ? 1 : 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) {
            userMeals = new ConcurrentHashMap<>();
            repository.put(userId, userMeals);
        }
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        if (get(meal.getId(), userId) == null) {
            return null;
        }
        // handle case: update, but not present in storage
        meal.setUserId(userId);
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) == null) {
            return false;
        }
        Map<Integer, Meal> userMeals = repository.get(userId);
        userMeals.remove(id);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) {
            return null;
        }
        Meal meal = userMeals.get(id);
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) {
            return Collections.emptyList();
        }
        return filteredByPredicateAndSorted(meal -> true, userMeals.values());
    }

    @Override
    public List<Meal> getAllFilter(LocalDate dateFrom, LocalDate dateTo, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals == null) {
            return Collections.emptyList();
        }
        return filteredByPredicateAndSorted(meal ->
                meal.getDate().compareTo(dateFrom) >= 0
                        && meal.getDate().compareTo(dateTo) <= 0, userMeals.values());
    }

    private List<Meal> filteredByPredicateAndSorted(Predicate<Meal> filter, Collection<Meal> meals) {
        return meals.stream().filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

