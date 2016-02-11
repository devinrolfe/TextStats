package com.nullnothing.relationshipstats.enumsOrConstants;

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
