package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(10,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> map = mealList
                .stream()
                .collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));

        return mealList
                .stream()
                .filter(um -> TimeUtil.isBetween(um.getTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(um, map.get(um.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> map = new HashMap<>();
        mealList.forEach(um -> map.merge(um.getDate(), um.getCalories(), Integer::sum));

        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();

        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                UserMealWithExceed mealWithExceed = new UserMealWithExceed(meal, map.get(meal.getDate()) > caloriesPerDay);
                mealWithExceedList.add(mealWithExceed);
            }
        }

        return mealWithExceedList;
    }
}
