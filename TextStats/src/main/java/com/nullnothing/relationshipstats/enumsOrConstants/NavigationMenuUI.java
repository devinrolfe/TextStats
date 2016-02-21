package com.nullnothing.relationshipstats.enumsOrConstants;

public enum NavigationMenuUI {
    // TODO make sure the ui navigation menu uses these names
    Top_NUM_CONTACTS("Top # Of Contacts"),
    MESSAGE_TYPES("Message Types"),
    INTERVALS("Intervals"),
    PERIOD("Periods"),
    OTHER("Others")
    ;

    private final String option;

    NavigationMenuUI(final String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }

}
