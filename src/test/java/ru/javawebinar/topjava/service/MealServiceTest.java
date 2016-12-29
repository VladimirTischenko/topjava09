package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Vladimir on 26.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL1_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(ADMIN_MEAL1_ID, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ADMIN_MEAL1_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL2), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(ADMIN_MEAL1_ID, USER_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 13, 0),
                LocalDateTime.of(2015, 5, 31, 13, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2), meals);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> all = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL1);
        updated.setId(USER_MEAL1_ID);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(600);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(USER_MEAL1_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(ADMIN_MEAL1, USER_ID);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2016, 12, 26, 18, 30), "Новый ужин", 600);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(created, ADMIN_MEAL2, ADMIN_MEAL1), service.getAll(ADMIN_ID));
    }

}