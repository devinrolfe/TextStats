package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.TextMessage;
import com.nullnothing.relationshipstats.Enums.TimePeriod;

public class WeekDecorator extends MessageDecorator {

    public WeekDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.WEEK; }
}
