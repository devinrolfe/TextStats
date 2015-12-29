package com.nullnothing.relationshipstats.EnumsOrConstants;

public enum TimeSeperator {

    MINUTE("minute"),
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year")
    ;

    private final String dataSeperator;

    private TimeSeperator(final String dataSeperator) {
        this.dataSeperator = dataSeperator;
    }

    @Override
    public String toString() {
        return dataSeperator;
    }

}
