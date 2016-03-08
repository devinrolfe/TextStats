package com.nullnothing.relationshipstats.dataPoint;

import com.nullnothing.relationshipstats.dataPoint.dataPointUtil;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.dataStructures.ContactNode;
import com.nullnothing.relationshipstats.enumsOrConstants.CalendarHelper;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.Constants;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
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
            if (calendarValue == Calendar.HOUR) {
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
       if (intervalOrPeriod.equals(TimeInterval.HOUR.toString())) {
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

    public static long subtractIntervalFromTime(Calendar calposer, int calendarIntValue, Category category) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(calposer.getTimeInMillis());

        if (calendarIntValue == Calendar.WEEK_OF_MONTH) {
            cal.add(Calendar.DATE, -7);
        }
        else if (calendarIntValue == -1234567890) { // get time from text messages using category
            return getFirstTextMessageTime(category);
        }
        else {
            cal.add(calendarIntValue, -1);
        }
//        else if(calendarIntValue == Calendar.YEAR){
//            cal.add(Calendar.YEAR, -1);
//        }
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

    public static void setYValues(ContactLinkedList contactLinkedList, List<String> xValueList, TimeInterval interval, TimePeriod period, Category category) {
        ContactNode node = contactLinkedList.getHead();

        // TODO Selecting minutes on Time interval will eventually crash app, takes too long need solution
        // CAME to conclusion that minutes is not that helpful, but I should still implement about
        // 8 threads in the background that can handle the work to make everything else smoother


        while (node != null) {
            dataPointUtil.run(node, interval, period, category);
            node = node.next;
        }



//        List<Thread> threads = new ArrayList();
//
//        // TODO : THIS IS CAUSING A BUG becase too many threads are being generated
//        while (node != null) {
//            Thread thread = new Thread(new DataPointThread(node, interval, period, category));
//            threads.add(thread);
//            thread.start();
//            node = node.next;
//        }
//
//        for (Thread thread: threads) {
//            try {
//                thread.join();
//            } catch(InterruptedException e) {
//
//            }
//        }
    }

}
