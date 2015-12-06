package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.TextMessage;
import com.nullnothing.relationshipstats.Enums.TimePeriod;

public class MonthDecorator extends MessageDecorator {

    public MonthDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.MONTH; }
}
