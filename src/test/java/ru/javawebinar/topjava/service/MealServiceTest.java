package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private long start;
    private String methodName;
    private static Map<String, Long> executionTime = new LinkedHashMap<>();

    @Autowired
    private MealService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before(){
        start = System.currentTimeMillis();
    }

    @After
    public void after(){
        long delta = System.currentTimeMillis() - start;
        System.out.println("executionTime: " + delta);
        executionTime.put(methodName, delta);
        System.out.println("__________________________________________________");
        System.out.println();
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("__________________________________________________");
        System.out.println();
        System.out.println("EXECUTE TIME");
        System.out.println();
        System.out.printf("%-20s", "Method Name");
        System.out.println("Time");
        System.out.println();
        for(Map.Entry entry : executionTime.entrySet()){
            System.out.printf("%-20s", entry.getKey());
            System.out.printf("%4s", entry.getValue());
            System.out.println();
        }
        System.out.println("__________________________________________________");
        System.out.println();
    }

    @Test
    public void testDelete() throws Exception {
        getMethodName();
        service.delete(MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
    }

    @Test/*(expected = NotFoundException.class)*/
    public void testDeleteNotFound() throws Exception {
        getMethodName();
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void testSave() throws Exception {
        getMethodName();
        Meal created = getCreated();
        service.save(created, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test
    public void testGet() throws Exception {
        getMethodName();
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
    }

    @Test/*(expected = NotFoundException.class)*/
    public void testGetNotFound() throws Exception {
        getMethodName();
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        getMethodName();
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test/*(expected = NotFoundException.class)*/
    public void testUpdateNotFound() throws Exception {
        getMethodName();
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        getMethodName();
        MATCHER.assertCollectionEquals(MEALS, service.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        getMethodName();
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
    }

    private void getMethodName(){
        methodName = new Exception().getStackTrace()[1].getMethodName();
    }
}