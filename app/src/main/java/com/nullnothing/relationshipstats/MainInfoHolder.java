package com.nullnothing.relationshipstats;


import java.util.HashMap;

public class MainInfoHolder {

    private static HashMap<String, ContactInfoHolder> map = new HashMap<String, ContactInfoHolder>();


    private static final MainInfoHolder holder = new MainInfoHolder();
    public static MainInfoHolder getInstance() { return holder; }
    public static HashMap<String, ContactInfoHolder> getContacts() { return map; }

    public static void addContact(String raw_contact_id, ContactInfoHolder contactInfoHolder) {
        map.put(raw_contact_id, contactInfoHolder);
    }


}
