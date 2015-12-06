package com.nullnothing.relationshipstats.Enums;

public enum TimePeriod {

    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year"),
    ALL_TIME("all time")
    ;

    private final String period;

    private TimePeriod(final String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return period;
    }

}
