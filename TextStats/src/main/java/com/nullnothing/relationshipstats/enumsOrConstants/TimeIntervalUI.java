package com.nullnothing.relationshipstats.enumsOrConstants;

public enum TimeIntervalUI {
    // TODO make sure the ui navigation menu uses these names

    MINUTE("Minute"),
    HOUR("Hour"),
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year")
    ;

    private final String intervalUI;

    TimeIntervalUI(final String intervalUI) {
        this.intervalUI = intervalUI;
    }

    @Override
    public String toString() {
        return intervalUI;
    }
}
