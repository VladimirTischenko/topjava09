package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladimir on 15.12.2016.
 */
public interface MealDao {
    List<MealWithExceed> getAll();
    Meal getMealById(int mealId);
    void add(Meal meal);
    void delete(int mealId);
    void update(int id, LocalDateTime localDateTime, String description, int calories);
}
