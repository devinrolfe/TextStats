package com.nullnothing.relationshipstats;


import java.util.ArrayList;

public class ContactInfoHolder {

    private String name;
    private String primaryPhoneNumber;
    private ArrayList<String> otherPhoneNumbers;
    private String raw_contact_id;
    private ArrayList<SentMessage> sentMessages;
    private ArrayList<ReceivedMessage> receivedMessages;

    private int textReceived = 0;
    private int textSent = 0;


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
    public int getTextReceivedCount() { return this.textReceived; }
    public ArrayList getReceivedMessages() { return this.receivedMessages; }
    public int getTextSentCount() { return this.textSent; }
    public ArrayList getSentMessages() { return this.sentMessages; }

    public void addTextMessage(TextMessage msg) {

        if (msg instanceof SentMessage) {
            sentMessages.add((SentMessage)msg);
            textSent++;
        }
        else if (msg instanceof ReceivedMessage) {
            receivedMessages.add((ReceivedMessage)msg);
            textReceived++;
        }
    }

}
