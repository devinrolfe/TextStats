package com.nullnothing.relationshipstats;


import java.util.ArrayList;

public class ContactInfoHolder {

    private String name;
    private String primaryPhoneNumber;
    private ArrayList<String> phoneNumbers = new ArrayList<String>();
    private String raw_contact_id;
    private ArrayList<TextMessage> messages = new ArrayList<TextMessage>();


    public ContactInfoHolder(String name, ArrayList<String> phoneNumbers, String raw_contact_id) {
        this.name = name;
        this.primaryPhoneNumber = phoneNumbers.get(0);
        this.phoneNumbers = phoneNumbers;
        this.raw_contact_id = raw_contact_id;
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
