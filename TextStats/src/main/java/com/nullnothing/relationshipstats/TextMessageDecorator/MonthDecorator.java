package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.TextMessageObjects.TextMessage;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

public class MonthDecorator extends MessageDecorator {

    public MonthDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.MONTH; }
}
