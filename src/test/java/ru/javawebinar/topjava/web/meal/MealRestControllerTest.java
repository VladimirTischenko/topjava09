package ru.javawebinar.topjava.web.meal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1_ID;

/**
 * Created by Vladimir on 28.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealRestControllerTest {

    @Autowired
    private MealRestController controller;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test (expected = NotFoundException.class)
    public void updateAlienMeal() throws Exception {
        Meal updated = new Meal(ADMIN_MEAL1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        controller.update(updated, ADMIN_MEAL1_ID);
    }

}