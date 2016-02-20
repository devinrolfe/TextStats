package com.nullnothing.relationshipstats.enumsOrConstants;

public enum Category {

    SENTANDRECEIVEDMSG("sent&received"),
    SENTMSG("sentmsg"),
    RECEIVEDMSG("receivedmsg")
    ;

    private final String category;

    Category(final String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

    public static Category getValueOf(String str) {

        if (str.equals(Category.SENTANDRECEIVEDMSG.toString())) {
            return Category.SENTANDRECEIVEDMSG;
        }
        if (str.equals(Category.SENTMSG.toString())) {
            return Category.SENTMSG;
        }
        if (str.equals(Category.RECEIVEDMSG.toString())) {
            return Category.RECEIVEDMSG;
        }
        return null;
    }

}
