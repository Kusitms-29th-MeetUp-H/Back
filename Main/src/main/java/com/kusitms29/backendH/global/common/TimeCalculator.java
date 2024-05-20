package com.kusitms29.backendH.global.common;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

@Slf4j
public class TimeCalculator {
    public static String calculateTimeDifference(LocalDateTime date) {
        log.info("date :: {}", date);
        LocalDateTime now = LocalDateTime.now();
        log.info("now :: {}", now);
        Duration duration = Duration.between(date, now);

        long minutes = duration.toMinutes();
        if (minutes < 1) {
            return "방금 전";
        }

        if (minutes < 60) {
            return minutes + "분 전";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + "시간 전";
        }

        long days = duration.toDays();
        if (days < 7) {
            return days + "일 전";
        }

        long weeks = days / 7;
        if (weeks < 4) {
            return weeks + "주 전";
        }

        long months = days / 30;
        if (months < 12) {
            return months + "달 전";
        }

        long years = months / 12;
        return years + "년 전";
    }

    public static DayOfWeek convertStringToDayOfWeek(String day) {
        switch (day) {
            case "월":
                return DayOfWeek.MONDAY;
            case "화":
                return DayOfWeek.TUESDAY;
            case "수":
                return DayOfWeek.WEDNESDAY;
            case "목":
                return DayOfWeek.THURSDAY;
            case "금":
                return DayOfWeek.FRIDAY;
            case "토":
                return DayOfWeek.SATURDAY;
            case "일":
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + day);
        }
    }

    public static LocalDateTime getNextWeekDate(DayOfWeek dayOfWeek) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeekDate = today.with(TemporalAdjusters.next(dayOfWeek));
        return LocalDateTime.of(nextWeekDate, LocalTime.MIDNIGHT);
    }

}
