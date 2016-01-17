package com.nullnothing.relationshipstats;

import com.nullnothing.relationshipstats.EnumsOrConstants.CalandarHelper;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

import java.util.Calendar;
import java.util.List;

public class DataPointCollection {

    private DataPointCollection() {
        throw new AssertionError();
    }

    // TODO: Need to implement
    public static List<DataPoint> getXValues(TimeInterval separator) {

        long currentTime = Calendar.getInstance().getTimeInMillis();
        // TODO : need to use currentTime to go back by 'interval' and then
        // stop when period is reached. Adding those dates each time to a list
        return null;

    }



}
