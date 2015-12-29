package com.nullnothing.relationshipstats.EnumsOrConstants;

import java.util.Calendar;

public enum CalandarHelper {
    INSTANCE;

    private Calendar cal = Calendar.getInstance();
    private long currentTime;

    private long dayAgo;
    private long weekAgo;
    private long monthAgo;
    private long yearAgo;
    {
        cal.setTimeInMillis(System.currentTimeMillis());

        cal.add(Calendar.DATE, -1);
        dayAgo = cal.getTimeInMillis();

        cal.add(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -7);
        weekAgo = cal.getTimeInMillis();

        cal.add(Calendar.DATE, 7);
        cal.add(Calendar.MONTH, -1);
        monthAgo = cal.getTimeInMillis();

        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.YEAR, -1);
        yearAgo = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, 1);
    }

    public TimePeriod howOld(long timestamp) {
        if(timestamp > dayAgo) {
            return TimePeriod.DAY;
        }
        else if (timestamp > weekAgo) {
            return TimePeriod.WEEK;
        }
        else if (timestamp > monthAgo) {
            return TimePeriod.MONTH;
        }
        else if (timestamp > yearAgo) {
            return TimePeriod.YEAR;
        }
        else{
            return TimePeriod.ALL_TIME;
        }
    }

}
