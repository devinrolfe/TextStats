package com.nullnothing.relationshipstats.DataStructures;

import com.nullnothing.relationshipstats.DataStorageObjects.ContactInfoHolder;

import java.util.Map;

public class ContactNode {

    private ContactInfoHolder data;
    public ContactNode next;

    private Map<String, Integer> ySentValues;
    private Map<String, Integer> yReceivedValues;

    public ContactNode(ContactInfoHolder contact) {
        data = contact;
        next = null;
    }

    public ContactInfoHolder getData() { return this.data; }

    public void setSentYValues(DefaultHashMap map) { ySentValues = map; }
    public Map getSentYValues() { return ySentValues; }

    public void setReceivedYValues(DefaultHashMap map) { yReceivedValues = map; }
    public Map getReceivedYValues() { return yReceivedValues; }
}
