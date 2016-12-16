package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vladimir on 12.12.2016.
 */
public class MemoryMealDao implements MealDao {
    private static List<Meal> meals = MealsUtil.meals;

    private static AtomicInteger idCounter;

    public static void setIdCounter(AtomicInteger idCounter) {
        MemoryMealDao.idCounter = idCounter;
    }

    @Override
    public List<MealWithExceed> getAll(){
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    public Meal getMealById(int mealId) {
        for (Meal meal : meals) {
            if (meal.getId() == mealId){
                return meal;
            }
        }
        return null;
    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void delete(int mealId) {
        meals.removeIf(meal -> meal.getId() == mealId);
    }

    @Override
    public void update(int id, LocalDateTime localDateTime, String description, int calories) {
        for (Meal meal : meals) {
            if (meal.getId() == id){
                meal.setDateTime(localDateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
            }
        }
    }

    public static int getId(){
        return idCounter.getAndIncrement();
    }
}
