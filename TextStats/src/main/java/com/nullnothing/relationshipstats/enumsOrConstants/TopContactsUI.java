package com.nullnothing.relationshipstats.enumsOrConstants;

public enum TopContactsUI {
    ONE("1"),
    THREE("3"),
    FIVE("5"),
    TEN("10"),
    TWENTY("20")
    ;
    private final String topContactUI;

    TopContactsUI(final String topContactUI) {
        this.topContactUI = topContactUI;
    }

    @Override
    public String toString() {
        return topContactUI;
    }
}
