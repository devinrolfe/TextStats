package com.nullnothing.relationshipstats.enumsOrConstants;

public enum FragmentName {

    GraphFragment("Graph Fragment"),
    CardFragment("Card Fragment"),
    RawDataFragment("Raw Data Fragment")
    ;

    private final String fragmentName;

    private FragmentName(final String name) {
        fragmentName = name;
    }

    @Override
    public String toString() {
        return fragmentName;
    }

}
