package com.nullnothing.relationshipstats.textMessageDecorator;

import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.textMessageObjects.TextMessage;

public class MonthDecorator extends MessageDecorator {

    public MonthDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.MONTH; }
}
