package com.nullnothing.relationshipstats.dataStorageObjects;


import com.nullnothing.relationshipstats.textMessageObjects.TextMessage;

import java.util.ArrayList;

public class MainInfoHolder {

    private static MainInfoHolder holder;
    public static int contactCount = 0;

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
    public ArrayList getContactList() { return contactList; }

    /**
     * Method adds contact information, will overwrite previous contact infomation if it exists
     */
    public void addContact(ContactInfoHolder contactInfoHolder) {
        if(this.contactMap.get(contactInfoHolder.getId()) == null) { //make sure not to overwrite previous contact
            this.contactMap.put(contactInfoHolder.getId(), contactInfoHolder);
            this.contactList.add(contactInfoHolder.getId());
        }
    }

    /**
     * Finds where to insert text message and appends it.
     */
    public void addTextMessage(String id, String from, TextMessage tm) {

        if(this.contactMap.get(id) != null) {
            // we want contactMap to append tm to ConactInfoHolder correct arraylist
            this.contactMap.put(id, tm);
        }
        else{
            // contact doesnt exist, make one since its not saved on phones contacts?
//            Log.d("addTextMessage ", from + ": " + id + " " + tm.getMessage());
        }


    }
}
