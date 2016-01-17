package com.nullnothing.relationshipstats.TextMessageDecorator;

import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.TextMessageObjects.TextMessage;

public abstract class MessageDecorator extends TextMessage {

    protected TextMessage textMessage;

    public MessageDecorator(TextMessage mTextMessage) {
        this.textMessage = mTextMessage;
    }

    public abstract TimePeriod getPeriod();

    public TextMessage getTextMessage(){ return textMessage; }

    @Override
    public String getMessage() { return textMessage.getMessage(); }

    @Override
    public long getTimestamp() { return textMessage.getTimestamp(); }
}
