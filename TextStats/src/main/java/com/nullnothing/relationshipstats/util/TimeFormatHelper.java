package com.nullnothing.relationshipstats.util;


import com.nullnothing.relationshipstats.EnumsOrConstants.TimeInterval;

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

        if(intervalRank == TimeInterval.MINUTE.getRank()) {
            return new SimpleDateFormat("h:mm a MMMM/dd/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.HOUR.getRank()) {
            return new SimpleDateFormat("h a MMMM/dd/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.WEEK.getRank()) {
            return new SimpleDateFormat("Week E, MMMM/dd/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.DAY.getRank()) {
            return new SimpleDateFormat("MMMM/dd/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.MONTH.getRank()) {
            return new SimpleDateFormat("MMMM/yyyy").format(date);
        }
        else if(intervalRank == TimeInterval.YEAR.getRank()) {
            return new SimpleDateFormat("yyyy").format(date);
        }
        else{
            return new SimpleDateFormat("h:mm a MMMM/dd/yyyy").format(date);
        }
    }
}
