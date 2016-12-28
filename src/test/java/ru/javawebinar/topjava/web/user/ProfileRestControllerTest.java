package ru.javawebinar.topjava.web.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

/**
 * Created by Vladimir on 27.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProfileRestControllerTest {

    @Autowired
    private ProfileRestController controller;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test(expected = NotFoundException.class)
    public void getAlienUser() throws Exception {
        controller.get(ADMIN_ID == AuthorizedUser.id() ? ADMIN_ID : 0);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlienUser() throws Exception {
        controller.delete(ADMIN_ID == AuthorizedUser.id() ? ADMIN_ID : 0);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlienUser() throws Exception {
        User updated = new User(ADMIN);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        controller.update(updated);
    }

}