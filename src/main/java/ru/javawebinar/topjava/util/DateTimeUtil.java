package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDateOrDefault(String date, LocalDate defaultDate) {
        if (date.isEmpty())
            return defaultDate;
        return LocalDate.parse(date);
    }

    public static LocalTime parseTimeOrDefault(String time, LocalTime defaultTime) {
        if (time.isEmpty())
            return defaultTime;
        return LocalTime.parse(time);
    }
}

