package com.nullnothing.relationshipstats;


import android.util.Log;

import java.util.ArrayList;

public class ContactInfoHolder {

    private String name;
    private String primaryPhoneNumber;
    private ArrayList<String> phoneNumbers;
    private String raw_contact_id;
    private ArrayList<SentMessage> sentMessages;
    private ArrayList<ReceivedMessage> receivedMessages;


    public ContactInfoHolder(String raw_contact_id, String name, ArrayList<String> phoneNumbers) {
        this.name = name;
        this.primaryPhoneNumber = phoneNumbers.get(0);
        this.phoneNumbers = phoneNumbers;
        this.raw_contact_id = raw_contact_id;

        sentMessages = new ArrayList<SentMessage>();
        receivedMessages = new ArrayList<ReceivedMessage>();

        Log.d("ContactName", this.name);
        Log.d("Prim#", this.primaryPhoneNumber);
        for(int i=1; i < phoneNumbers.size();i++){
            Log.d("Other#'s", phoneNumbers.get(i));
        }


    }

    public String getId() { return this.raw_contact_id; }
    public String getPrimaryPhoneNumber() { return this.primaryPhoneNumber; }
    public String getName() { return this.name; }

    public void addTextMessage(TextMessage msg) {

        if (msg instanceof SentMessage) {
           sentMessages.add((SentMessage)msg);
        }
        else if (msg instanceof ReceivedMessage) {
            receivedMessages.add((ReceivedMessage)msg);
        }
    }

    public void print() {
        System.out.println("-------------------------------------");
        System.out.println("Name: " + name);
        System.out.println("Primary#: " + primaryPhoneNumber);
        for(int i = 1; i<phoneNumbers.size(); i++){
            System.out.println("other#: " + phoneNumbers.get(i));
        }
        System.out.println("ID: " + raw_contact_id);
        System.out.println("-------------------------------------");
    }


}
