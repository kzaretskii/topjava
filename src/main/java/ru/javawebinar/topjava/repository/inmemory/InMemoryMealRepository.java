package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUserId(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal mealInRepository = get(meal.getId(), userId);
        if (!isCorrectUserIdAndNotNull(mealInRepository, userId))
            return null;
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (!isCorrectUserIdAndNotNull(meal, userId))
            return false;
        repository.remove(id);
        return meal != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (!isCorrectUserIdAndNotNull(meal, userId))
            return null;
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filteredByUserId(repository.values(), userId);
    }

    private List<Meal> filteredByUserId(Collection<Meal> meals, int userId) {
        return meals.stream().filter(meal -> meal.getUserId() == userId)
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    private boolean isCorrectUserIdAndNotNull(Meal meal, int userId) {
        return meal != null && meal.getUserId() == userId;
    }
}

