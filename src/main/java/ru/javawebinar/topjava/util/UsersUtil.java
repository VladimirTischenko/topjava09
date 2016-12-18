package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User("User", "user@gmail.com", "user", Role.ROLE_USER)
    );
}
