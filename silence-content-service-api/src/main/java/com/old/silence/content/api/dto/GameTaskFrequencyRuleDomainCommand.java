package com.old.silence.content.api.dto;


import com.old.silence.content.domain.enums.EventGameTaskCompletionLimitPeriodType;

public class GameTaskFrequencyRuleDomainCommand {

    private EventGameTaskCompletionLimitPeriodType periodType;

    private Long maxTimes;

    private Long maxTimesPerPeriod;

    private Long maxTimesPerPerson;

    private Long maxTimesPerPersonPerPeriod;

    private Long maxTimesPerPersonPerDay;

    public EventGameTaskCompletionLimitPeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(EventGameTaskCompletionLimitPeriodType periodType) {
        this.periodType = periodType;
    }

    public Long getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(Long maxTimes) {
        this.maxTimes = maxTimes;
    }

    public Long getMaxTimesPerPeriod() {
        return maxTimesPerPeriod;
    }

    public void setMaxTimesPerPeriod(Long maxTimesPerPeriod) {
        this.maxTimesPerPeriod = maxTimesPerPeriod;
    }

    public Long getMaxTimesPerPerson() {
        return maxTimesPerPerson;
    }

    public void setMaxTimesPerPerson(Long maxTimesPerPerson) {
        this.maxTimesPerPerson = maxTimesPerPerson;
    }

    public Long getMaxTimesPerPersonPerPeriod() {
        return maxTimesPerPersonPerPeriod;
    }

    public void setMaxTimesPerPersonPerPeriod(Long maxTimesPerPersonPerPeriod) {
        this.maxTimesPerPersonPerPeriod = maxTimesPerPersonPerPeriod;
    }

    public Long getMaxTimesPerPersonPerDay() {
        return maxTimesPerPersonPerDay;
    }

    public void setMaxTimesPerPersonPerDay(Long maxTimesPerPersonPerDay) {
        this.maxTimesPerPersonPerDay = maxTimesPerPersonPerDay;
    }
}
