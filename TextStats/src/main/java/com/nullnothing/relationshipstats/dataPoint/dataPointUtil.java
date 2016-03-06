package com.nullnothing.relationshipstats.dataPoint;

import com.nullnothing.relationshipstats.dataStructures.ContactNode;
import com.nullnothing.relationshipstats.dataStructures.DefaultHashMap;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.textMessageDecorator.MessageDecorator;
import com.nullnothing.relationshipstats.util.TimeFormatHelper;

import java.util.Map;

public class dataPointUtil {

    private dataPointUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void run(ContactNode node, TimeInterval interval, TimePeriod period, Category category) {
        if(category == Category.SENTANDRECEIVEDMSG || category == Category.SENTMSG) {
            node.setSentYValues(setYValuesHelper(node, interval, period, true));
        }
        if(category == Category.SENTANDRECEIVEDMSG || category == Category.RECEIVEDMSG) {
            node.setReceivedYValues(setYValuesHelper(node, interval, period, false));
        }
    }

    private static DefaultHashMap setYValuesHelper(ContactNode node, TimeInterval interval, TimePeriod period, boolean isSent) {
        Map<String, Integer> yValues = new DefaultHashMap<>(0);
        for( Object textMessage : isSent ? node.getData().getSentMessages() : node.getData().getReceivedMessages()) {
            MessageDecorator msg = (MessageDecorator) textMessage;

            if (msg.getPeriod().getRank() <= period.getRank()) {
                String point = TimeFormatHelper.TimeToDataPoint(msg.getTimestamp(), interval);
                int pointYValue = yValues.get(point) + 1;
                yValues.put(point, pointYValue);
            }
        }
        return (DefaultHashMap) yValues;
    }



}
