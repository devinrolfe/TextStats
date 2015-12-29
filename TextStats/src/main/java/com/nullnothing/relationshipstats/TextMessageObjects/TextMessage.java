package com.nullnothing.relationshipstats.TextMessageObjects;


import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

import java.util.Calendar;

public abstract class TextMessage {

    private long timestamp;
    private String message;
    private Calendar cal = Calendar.getInstance();

    public TextMessage() {
        // Would like to make this constructer not instainateable but can't because of decorator..
//        throw new AssertionError();
    }

    public TextMessage(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        cal.setTimeInMillis(System.currentTimeMillis());
    }

    public String getMessage() { return this.message; }
    public long getTimestamp() { return this.timestamp; }

    public TimePeriod getPeriod() { return TimePeriod.ALL_TIME; }

    public int getYear() { return cal.get(Calendar.YEAR);}
    public int getMonth() { return cal.get(Calendar.MONTH);}
    public int getDay() { return cal.get(Calendar.DATE);}
    public int getHour() { return cal.get(Calendar.AM_PM);}
    public int getMinute() { return cal.get(Calendar.MINUTE);}

}
