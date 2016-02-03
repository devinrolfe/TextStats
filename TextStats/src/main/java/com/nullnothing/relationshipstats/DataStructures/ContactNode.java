package com.nullnothing.relationshipstats.DataStructures;

import com.github.mikephil.charting.data.Entry;
import com.nullnothing.relationshipstats.DataStorageObjects.ContactInfoHolder;

import java.util.ArrayList;
import java.util.List;
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
    public ArrayList<Entry> getSentYValues(List<String> xValues) {
        ArrayList<Entry> yValues = new ArrayList<Entry>();

        int i = 0;
        for (String x : xValues) {
            yValues.add(new Entry(ySentValues.get(x), i));
            i++;
        }
        return yValues;
    }

    public void setReceivedYValues(DefaultHashMap map) { yReceivedValues = map; }
    public ArrayList<Entry> getReceivedYValues(List<String> xValues) {
        ArrayList<Entry> yValues = new ArrayList<Entry>();

        int i = 0;
        for (String x : xValues) {
            yValues.add(new Entry(yReceivedValues.get(x), i));
            i++;
        }
        return yValues;
    }
}
