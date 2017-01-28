package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */
@Transactional(readOnly = true)
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> USER_ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) -> Role.valueOf(rs.getString("role"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        Set<Role> roles = user.getRoles();
        List<String> strings = new ArrayList<>();

        for (Role role : roles) {
            strings.add(role.toString());
        }
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        }
        for (String string : strings) {
            jdbcTemplate.update("INSERT INTO user_roles (role, user_id) VALUES (?, ?)", string, user.getId());
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", USER_ROW_MAPPER, id);
        List<Role> roleList = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", ROLE_ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        return setRoles(user, roleList);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", USER_ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", USER_ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        List<Role> roleList = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", ROLE_ROW_MAPPER, user.getId());
        return setRoles(user, roleList);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", USER_ROW_MAPPER);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT id, role FROM users" +
                " LEFT JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email");
        while (sqlRowSet.next()) {
            int id = sqlRowSet.getInt("id");
            String s = sqlRowSet.getString("role");
            Role role = Role.valueOf(s);
            for(User user : users) {
                if (user.getId() == id) {
                    Set<Role> roles = user.getRoles();
                    if (roles == null) {
                        Set<Role> roleSet = new HashSet<>();
                        roleSet.add(role);
                        user.setRoles(roleSet);
                    } else {
                        roles.add(role);
                    }
                    break;
                }
            }
        }
        return users;
    }

    private User setRoles(User user, List<Role> roleList) {
        Set<Role> roleSet = new HashSet<>(roleList);
        if (roleSet.size() > 0) {
            user.setRoles(roleSet);
        }
        return user;
    }
}
