package com.nullnothing.relationshipstats.Enums;

public enum Category {

    SENTANDRECEIVEDMSG("sent&received"),
    SENTMSG("sentmsg"),
    RECEIVEDMSG("receivedmsg")
    ;

    private final String category;

    private Category(final String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

}
