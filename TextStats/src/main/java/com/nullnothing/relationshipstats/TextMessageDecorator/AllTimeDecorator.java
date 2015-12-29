package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.TextMessageObjects.TextMessage;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

public class AllTimeDecorator extends MessageDecorator {

    public AllTimeDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.ALL_TIME; }
}
