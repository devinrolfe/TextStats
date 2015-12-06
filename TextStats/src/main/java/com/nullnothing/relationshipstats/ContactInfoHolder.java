package com.nullnothing.relationshipstats;


import com.nullnothing.relationshipstats.Enums.Category;
import com.nullnothing.relationshipstats.Enums.TimePeriod;

import java.util.ArrayList;

public class ContactInfoHolder {

    private String name;
    private String primaryPhoneNumber;
    private ArrayList<String> otherPhoneNumbers;
    private String raw_contact_id;
    private ArrayList<SentMessage> sentMessages;
    private ArrayList<ReceivedMessage> receivedMessages;

    private int allTextReceived = 0;
    private int dayTextReceived = 0;
    private int weekTextReceived = 0;
    private int monthTextReceived = 0;
    private int yearTextReceived = 0;

    private int allTextSent = 0;
    private int dayTextSent = 0;
    private int weekTextSent = 0;
    private int monthTextSent = 0;
    private int yearTextSent = 0;


    public ContactInfoHolder(String raw_contact_id, String name, ArrayList<String> phoneNumbers) {
        this.name = name;
        try {
            this.primaryPhoneNumber = phoneNumbers.get(0);
            phoneNumbers.remove(0);
            this.otherPhoneNumbers = phoneNumbers;
        } catch (Exception e) {
            // no phone numbers assigned to contact, just assign to null
            this.primaryPhoneNumber = null;
            this.otherPhoneNumbers = new ArrayList<String>();
        }
        this.raw_contact_id = raw_contact_id;

        sentMessages = new ArrayList<SentMessage>();
        receivedMessages = new ArrayList<ReceivedMessage>();
    }

    public String getId() { return this.raw_contact_id; }
    public String getPrimaryPhoneNumber() { return this.primaryPhoneNumber; }
    public String getName() { return this.name; }

    public int getTextReceivedCount(TimePeriod timePeriod) {
        switch (timePeriod) {
            case ALL_TIME:
                return this.allTextReceived;
            case YEAR:
                return this.yearTextReceived;
            case MONTH:
                return this.monthTextReceived;
            case WEEK:
                return this.weekTextReceived;
            case DAY:
                return this.dayTextReceived;
            default:
                throw new AssertionError();
        }
    }

    public ArrayList getReceivedMessages() { return this.receivedMessages; }

    public int getTextSentCount(TimePeriod timePeriod) {
        switch (timePeriod) {
            case ALL_TIME:
                return this.allTextSent;
            case YEAR:
                return this.yearTextSent;
            case MONTH:
                return this.monthTextSent;
            case WEEK:
                return this.weekTextSent;
            case DAY:
                return this.dayTextSent;
            default:
                throw new AssertionError("incorrect period given.");
        }
    }
    public ArrayList getSentMessages() { return this.sentMessages; }

    public int getTextBothCount(TimePeriod timePeriod) {
        switch (timePeriod) {
            case ALL_TIME:
                return this.yearTextSent + this.allTextReceived;
            case YEAR:
                return this.yearTextSent + this.yearTextReceived;
            case MONTH:
                return this.monthTextSent + this.monthTextReceived;
            case WEEK:
                return this.weekTextSent + this.weekTextReceived;
            case DAY:
                return this.dayTextSent + this.dayTextReceived;
            default:
                throw new AssertionError("incorrect period given.");
        }
    }

    public int getTextCount(Category category, TimePeriod timePeriod) {
        switch (category) {
            case SENTANDRECEIVEDMSG:
                return this.getTextBothCount(timePeriod);
            case SENTMSG:
                return this.getTextSentCount(timePeriod);
            case RECEIVEDMSG:
                return this.getTextReceivedCount(timePeriod);
            default:
                throw new AssertionError("incorrect period given.");
        }
    }

    public void addTextMessage(TextMessage msg, TimePeriod timePeriod) {

        if (msg instanceof SentMessage) {
            sentMessages.add((SentMessage)msg);

            switch (timePeriod) {
                case DAY:
                    dayTextSent++;
                case WEEK:
                    weekTextSent++;
                case MONTH:
                    monthTextSent++;
                case YEAR:
                    yearTextSent++;
                default:
                    allTextSent++;
                    break;
            }
        }
        else if (msg instanceof ReceivedMessage) {
            receivedMessages.add((ReceivedMessage)msg);

            switch (timePeriod) {
                case DAY:
                    dayTextReceived++;
                case WEEK:
                    weekTextReceived++;
                case MONTH:
                    monthTextReceived++;
                case YEAR:
                    yearTextReceived++;
                default:
                    allTextReceived++;
                    break;
            }
        }
    }

}