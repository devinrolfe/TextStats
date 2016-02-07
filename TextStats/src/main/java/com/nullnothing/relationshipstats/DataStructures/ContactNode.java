package com.nullnothing.relationshipstats.DataStructures;

import com.github.mikephil.charting.data.Entry;
import com.nullnothing.relationshipstats.DataStorageObjects.ContactInfoHolder;
import com.nullnothing.relationshipstats.graphing.EntryDataObject;

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
    public void setReceivedYValues(DefaultHashMap map) { yReceivedValues = map; }

    public ArrayList<Entry> getSentYValues(List<String> xValues) {
        return getYValues(xValues, ySentValues);
    }

    public ArrayList<Entry> getReceivedYValues(List<String> xValues) {
        return getYValues(xValues, yReceivedValues);
    }

    public ArrayList<Entry> getYValues(List<String> xValues, Map<String, Integer> map) {

        ArrayList<Entry> yValues = new ArrayList<Entry>();

        int i = 0;
        for (String x : xValues) {
            Entry entry = new Entry(map.get(x), i);
            entry.setData(new EntryDataObject(data));
            yValues.add(entry);
            i++;
        }
        return yValues;

    }
}
