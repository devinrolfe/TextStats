package com.nullnothing.relationshipstats.util;


import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatHelper {

    private TimeFormatHelper() {
        throw new AssertionError();
    }

    public static String TimeToDataPoint(long time, TimeInterval interval) {
        Date date = new Date(time);
        return getTimeFormat(date, interval);
    }

    private static String getTimeFormat(Date date, TimeInterval interval) {
        int intervalRank = interval.getRank();

        if(intervalRank == TimeInterval.HOUR.getRank()) {
            return new SimpleDateFormat("h a MM/dd/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.WEEK.getRank()) {
            return  "Week " +
                    new SimpleDateFormat("W").format(date) +
                    ", " +
                    new SimpleDateFormat("MM/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.DAY.getRank()) {
            return new SimpleDateFormat("MM/dd/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.MONTH.getRank()) {
            return new SimpleDateFormat("MM/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.YEAR.getRank()) {
            return new SimpleDateFormat("yyyy").format(date);
        }
        else{
            return new SimpleDateFormat("h:mm a MM/dd/yyyy").format(date);
        }
    }
}
