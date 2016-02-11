package com.nullnothing.relationshipstats.textMessageDecorator;

import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.textMessageObjects.TextMessage;

public class AllTimeDecorator extends MessageDecorator {

    public AllTimeDecorator(TextMessage mTextMessage) {
        super(mTextMessage);
    }

    @Override
    public TimePeriod getPeriod() { return TimePeriod.ALL_TIME; }
}
