package com.nullnothing.relationshipstats.enumsOrConstants;

public enum OtherUI {
    LEGEND("Enable/Disable Legend"),
    ;
    private final String otherUI;

    OtherUI(final String otherUI) {
        this.otherUI = otherUI;
    }

    @Override
    public String toString() {
        return otherUI;
    }
}
