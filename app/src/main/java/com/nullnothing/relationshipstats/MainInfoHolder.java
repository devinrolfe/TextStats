package com.nullnothing.relationshipstats;


import android.util.Log;

import java.util.ArrayList;

public class MainInfoHolder {

    private static MainInfoHolder holder;
    static int contactCount = 0;

    //private HashMap<String, ContactInfoHolder> contactMap =
    private HashMapContactInfoHolder contactMap = new HashMapContactInfoHolder(contactCount);
    private ArrayList<String> contactList = new ArrayList<String>();

    public MainInfoHolder(int contactCount) {
        this.contactCount = contactCount;
    }

    public static MainInfoHolder getInstance() {

        if (holder == null) {
            holder = new MainInfoHolder(contactCount);
        }
        return holder;
    }

    public  HashMapContactInfoHolder getContacts() { return contactMap; }

    /**
     * Method adds contact information, will overwrite previous contact infomation if it exists
     */
    public void addContact(ContactInfoHolder contactInfoHolder) {
        this.contactMap.put(contactInfoHolder.getPrimaryPhoneNumber(), contactInfoHolder);
        this.contactList.add(contactInfoHolder.getName());
    }

    /**
     * Finds where to insert text message and appends it.
     */
    public void addTextMessage(String id, String from, TextMessage tm) {

        if(this.contactMap.get(from) != null) {
            // we want contactMap to append tm to ConactInfoHolder correct arraylist
            this.contactMap.put(id, tm);
        }
        else{
            // contact doesnt exist, make one since its not saved on phones contacts
            Log.d("addTextMessage ", from + ": " + id + " " + tm.getMessage());
        }


    }
}
