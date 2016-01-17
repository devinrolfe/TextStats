package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.TextMessageObjects.TextMessage;

public class WeekDecorator extends MessageDecorator {

    public WeekDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.WEEK; }
}
