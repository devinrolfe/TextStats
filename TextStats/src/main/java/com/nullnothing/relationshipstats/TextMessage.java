package com.nullnothing.relationshipstats;


public class TextMessage {

    private long timestamp;
    private String message;
    private int wordCount;

    public TextMessage(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.wordCount = 1;
    }

    public String getMessage() { return this.message; }

}
