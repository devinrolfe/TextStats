package com.nullnothing.relationshipstats.graphing;

import com.nullnothing.relationshipstats.dataStorageObjects.ContactInfoHolder;

public class EntryDataObject {

    private String name;

    public EntryDataObject(ContactInfoHolder data) {
        name = data.getName();
    }

    public String getName() { return name; }


}
