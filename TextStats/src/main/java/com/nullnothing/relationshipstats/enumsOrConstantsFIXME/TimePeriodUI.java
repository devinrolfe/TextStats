package com.nullnothing.relationshipstats.enumsOrConstants;

public enum TimePeriodUI {
    // TODO make sure the ui navigation menu uses these names

    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year"),
    ALL_TIME("all time")
    ;


    private final String periodUI;

    TimePeriodUI(final String periodUI) {
        this.periodUI = periodUI;
    }

    @Override
    public String toString() {
        return periodUI;
    }
}
