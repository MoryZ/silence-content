package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

public enum EventGameTaskCompletionLimitPeriodType implements DescribedEnumValue<Byte> {

    DAY(1, "每日"){
        @Override
        public String generateRulePeriod() {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()).format(LocalDate.now());
        }

        @Override
        public String generateRulePeriodByQueryDate(LocalDate date) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()).format(date);
        }
    },
    WEEK(2, "每周"){
        @Override
        public String generateRulePeriod() {
            return ""+LocalDate.now().get(WeekFields.of(DayOfWeek.MONDAY,1).weekBasedYear())+
                    LocalDate.now().get(WeekFields.of(DayOfWeek.MONDAY,1).weekOfWeekBasedYear());
        }

        @Override
        public String generateRulePeriodByQueryDate(LocalDate date) {
            return ""+date.get(WeekFields.of(DayOfWeek.MONDAY,1).weekBasedYear())+
                    date.get(WeekFields.of(DayOfWeek.MONDAY,1).weekOfWeekBasedYear());
        }
    },
    MONTH(3, "每月"){
        @Override
        public String generateRulePeriod() {
            return DateTimeFormatter.ofPattern("yyyy-MM").withZone(ZoneId.systemDefault()).format(LocalDate.now());
        }

        @Override
        public String generateRulePeriodByQueryDate(LocalDate date) {
            return DateTimeFormatter.ofPattern("yyyy-MM").withZone(ZoneId.systemDefault()).format(date);
        }
    },
    YEAR(4, "每年"){
        @Override
        public String generateRulePeriod() {
            return DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault()).format(LocalDate.now());
        }

        @Override
        public String generateRulePeriodByQueryDate(LocalDate date) {
            return DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault()).format(date);
        }
    },
    GLOBAL(9, "一次性"){
        @Override
        public String generateRulePeriod() {
            return "global";
        }
        @Override
        public String generateRulePeriodByQueryDate(LocalDate date) {
            return "global";
        }
    };

    private final Byte value;

    private final String description;

    EventGameTaskCompletionLimitPeriodType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public abstract String generateRulePeriod();

    public abstract String generateRulePeriodByQueryDate(LocalDate date);
}
