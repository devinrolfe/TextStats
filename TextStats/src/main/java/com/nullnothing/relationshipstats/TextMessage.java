package com.nullnothing.relationshipstats;


public abstract class TextMessage {

    private long timestamp;
    private String message;
    private int wordCount;

    public TextMessage() {
        // User should never call this constructer... ideally this class should of been interface
    }

    public TextMessage(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.wordCount = 1;
    }

    public String getMessage() { return this.message; }
    public long getTimestamp() { return this.timestamp; }

    public TimePeriod getPeriod() { return TimePeriod.ALL_TIME; }

}
