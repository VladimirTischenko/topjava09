package ru.javawebinar.topjava;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class Profiles {
    private static final String
            POSTGRES = "postgres",
            HSQLDB = "hsqldb",
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String ACTIVE_DB = POSTGRES;
    public static final String ACTIVE_SPRING_PROFILE = DATAJPA;
}
