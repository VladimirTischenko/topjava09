package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;

/**
 * Created by Vladimir on 18.01.2017.
 */
@Repository
@Profile(Profiles.POSTGRES)
public class JdbcMealRepositoryImplPostgres extends JdbcMealRepositoryImpl {
    public JdbcMealRepositoryImplPostgres(DataSource dataSource) {
        super(dataSource);
    }
}
