package com.nullnothing.relationshipstats.TextMessageObjects;


import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

public abstract class TextMessage {

    private long timestamp;
    private String message;

    public TextMessage() {
        // Would like to make this constructer not instainateable but can't because of decorator..
//        throw new AssertionError();
    }

    public TextMessage(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getMessage() { return this.message; }
    public long getTimestamp() { return this.timestamp; }
    public TimePeriod getPeriod() { return TimePeriod.ALL_TIME; }
}
