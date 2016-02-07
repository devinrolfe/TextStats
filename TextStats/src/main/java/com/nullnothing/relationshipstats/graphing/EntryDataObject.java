package com.nullnothing.relationshipstats.graphing;

import com.nullnothing.relationshipstats.DataStorageObjects.ContactInfoHolder;

public class EntryDataObject {

    private String name;

    public EntryDataObject(ContactInfoHolder data) {
        name = data.getName();
    }

    public String getName() { return name; }


}
