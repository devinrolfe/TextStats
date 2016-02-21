package com.nullnothing.relationshipstats.enumsOrConstants;

public enum BuilderName {

    GRAPH_CHANGE_REQUEST_BUILDER("graph change request builder"),
    CARD_CHANGE_REQUEST_BUILDER("card change request builder")
    ;

    private final String name;

    BuilderName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}