package com.nullnothing.relationshipstats;

import com.nullnothing.relationshipstats.EnumsOrConstants.CalendarHelper;
import com.nullnothing.relationshipstats.EnumsOrConstants.Category;
import com.nullnothing.relationshipstats.EnumsOrConstants.Constants;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.util.TimeFormatHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataPointCollection {

    private DataPointCollection() {
        throw new AssertionError();
    }

    public static List<String> getXValues(TimeInterval interval, TimePeriod period, Category category) {
        List<String> xValuesList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(CalendarHelper.INSTANCE.getCurrentTime());

        long firstXTime = subtractIntervalFromTime(cal, convertStringIntervalOrPeriodToInt(period.toString()), category);

        int calendarValue = convertStringIntervalOrPeriodToInt(interval.toString());

        while(cal.getTimeInMillis() > firstXTime) {
            if (calendarValue == Calendar.MINUTE) {
                cal.add(Calendar.MINUTE, -1);
            }
            else if (calendarValue == Calendar.HOUR) {
                cal.add(Calendar.HOUR, -1);
            }
            else if (calendarValue == Calendar.DATE) {
                cal.add(Calendar.DATE, -1);
            }
            else if (calendarValue == Calendar.WEEK_OF_MONTH) {
                cal.add(Calendar.DATE, -7);
            }
            else if (calendarValue == Calendar.MONTH) {
                cal.add(Calendar.MONTH, -1);
            }
            else if (calendarValue == Calendar.YEAR) {
                cal.add(Calendar.YEAR, -1);
            }
            if (cal.getTimeInMillis() > firstXTime) xValuesList.add(0, TimeFormatHelper.TimeToDataPoint(cal.getTimeInMillis(), interval));
        }
        return xValuesList;
    }

    public static int convertStringIntervalOrPeriodToInt(String intervalOrPeriod) {
       if (intervalOrPeriod.equals(TimeInterval.MINUTE.toString())) {
           return Calendar.MINUTE;
       }
       else if (intervalOrPeriod.equals(TimeInterval.HOUR.toString())) {
           return Calendar.HOUR;
       }
       else if (intervalOrPeriod.equals(TimePeriod.DAY.toString())) {
           return Calendar.DATE;
       }
       else if (intervalOrPeriod.equals(TimePeriod.WEEK.toString())) {
            return Calendar.WEEK_OF_MONTH;
       }
       else if (intervalOrPeriod.equals(TimePeriod.MONTH.toString())) {
           return Calendar.MONTH;
       }
       else if (intervalOrPeriod.equals(TimePeriod.YEAR.toString())) {
           return Calendar.YEAR;
       }
       else if (intervalOrPeriod.equals(TimePeriod.ALL_TIME.toString())){
           return -1234567890; // THIS IS HORRIBLE DESIGN only used in subtractIntervalFromTime method, otherwise we got problems!!!
       }
       else {
           throw new IllegalArgumentException("ERROR, Interval or Period is incorrect, kill me");
       }
    }

    public static long subtractIntervalFromTime(Calendar cal, int calendarIntValue, Category category) {

        if (calendarIntValue == Calendar.WEEK_OF_MONTH) {
            cal.add(Calendar.DATE, -7);
        }
        else if (calendarIntValue == -1234567890) { // get time from text messages using category
            return getFirstTextMessageTime(category);
        }
        else {
            cal.add(calendarIntValue, -1);
        }
        return cal.getTimeInMillis();
    }

    public static long getFirstTextMessageTime(Category category) {
        switch (category) {
            case SENTANDRECEIVEDMSG:
                return Math.min(Constants.lastSentTimestamp, Constants.lastReceivedTimestamp);
            case SENTMSG:
                return Constants.lastSentTimestamp;
            case RECEIVEDMSG:
                return Constants.lastReceivedTimestamp;
            default:
                throw new IllegalArgumentException();
        }
    }

}
