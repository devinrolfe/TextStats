package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.TextMessage;
import com.nullnothing.relationshipstats.Enums.TimePeriod;

public class AllTimeDecorator extends MessageDecorator {

    public AllTimeDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.ALL_TIME; }
}
