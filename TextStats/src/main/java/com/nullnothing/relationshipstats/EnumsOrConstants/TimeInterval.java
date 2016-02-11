package com.nullnothing.relationshipstats.enumsOrConstants;

public enum TimeInterval {

    MINUTE("minute"),
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year")
    ;

    private final String dataInterval;
    private int rank;

    private TimeInterval(final String dataInterval) {
        this.dataInterval = dataInterval;

        switch(dataInterval) {
            case "minute":
                rank = 1;
                break;
            case "hour":
                rank = 2;
                break;
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
        return dataInterval;
    }
    public int getRank() { return rank; }

}
