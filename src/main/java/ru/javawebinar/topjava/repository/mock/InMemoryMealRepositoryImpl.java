package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        LOG.info("save " + meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete meal " + id);
        if (!repository.containsKey(id)) {
            return false;
        }
        boolean deleteMeal = (repository.get(id).getUserId() == userId);
        if (deleteMeal) {
            repository.remove(id);
        }
        return deleteMeal;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get meal " + id);
        if (!repository.containsKey(id)) {
            return null;
        }
        Meal meal = repository.get(id);
        return (meal.getUserId() == userId) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.info("getAllMeals");
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId()== userId)
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }
}

