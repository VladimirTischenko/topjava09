package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetween(T t, T t1, T t2) {
        return (t1 == null || t.compareTo(t1) >= 0) &&
                (t2 == null || t.compareTo(t2) <= 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate toLocalDate(String s) {
        return (s == null || s.equals("")) ? null : LocalDate.parse(s, DATE_FORMATTER);
    }

    public static LocalTime toLocalTime(String s) {
        return (s == null || s.equals("")) ? null : LocalTime.parse(s, TIME_FORMATTER);
    }
}
