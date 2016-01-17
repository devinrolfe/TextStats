package com.nullnothing.relationshipstats.EnumsOrConstants;

public enum TimePeriod {

    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year"),
    ALL_TIME("all time")
    ;


    private final String period;
    private int rank;

    private TimePeriod(final String period) {
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

}
