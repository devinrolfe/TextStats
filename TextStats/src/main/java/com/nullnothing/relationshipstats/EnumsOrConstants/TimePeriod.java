package com.nullnothing.relationshipstats.enumsOrConstants;

public enum TimePeriod {

    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year"),
    ALL_TIME("all time")
    ;


    private final String period;
    private int rank;

    TimePeriod(final String period) {
        this.period = period;

        switch(period) {
            case "week":
                rank = 3;
                break;
            case "day":
                rank = 4;
                break;
            case "month":
                rank = 5;
                break;
            case "year":
                rank = 6;
                break;
            case "all time":
                rank = 7;
                break;
        }
    }

    @Override
    public String toString() {
        return period;
    }

    public int getRank() { return rank; }

    public static TimePeriod getValueOf(String str) {

        if (str.equals(TimePeriod.DAY.toString()) || str.equals(TimePeriodUI.DAY.toString())) {
            return TimePeriod.DAY;
        }
        if (str.equals(TimePeriod.WEEK.toString()) || str.equals(TimePeriodUI.WEEK.toString())) {
            return TimePeriod.WEEK;
        }
        if (str.equals(TimePeriod.MONTH.toString()) || str.equals(TimePeriodUI.MONTH.toString())) {
            return TimePeriod.MONTH;
        }
        if (str.equals(TimePeriod.YEAR.toString()) || str.equals(TimePeriodUI.YEAR.toString())) {
            return TimePeriod.YEAR;
        }
        if (str.equals(TimePeriod.ALL_TIME.toString()) || str.equals(TimePeriodUI.ALL_TIME.toString())) {
            return TimePeriod.ALL_TIME;
        }
        return null;
    }

}
