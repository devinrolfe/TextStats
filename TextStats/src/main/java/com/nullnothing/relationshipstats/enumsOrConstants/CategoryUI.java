package com.nullnothing.relationshipstats.enumsOrConstants;

public enum CategoryUI {
    // TODO make sure the ui navigation menu uses these names
    BOTH("Both"),
    SENT("Sent"),
    RECEIVED("Received")
    ;

    private final String categoryUI;

    CategoryUI(final String category) {
        this.categoryUI = category;
    }

    @Override
    public String toString() {
        return categoryUI;
    }
}
