package com.nullnothing.relationshipstats.ActivityHelpers;

import com.nullnothing.relationshipstats.dataStorageObjects.MainInfoHolder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.dataStructures.ContactNode;

import java.util.HashMap;
import java.util.Map;

public class ContactsAvailability {

    // <RawId, ContactName>
    private Map<String, String> displayedContacts;
    private Map<String, String> notDisplayedContacts;

    public ContactsAvailability(ContactLinkedList contactLinkedList) {

        displayedContacts = new HashMap<String, String>();
        notDisplayedContacts = new HashMap<String, String>();

        createBeingUsedContactsMap(contactLinkedList);
        createNotBeingUsedContactsMap();
    }

    private void createBeingUsedContactsMap(ContactLinkedList contactLinkedList) {

        for (Map.Entry<String, ContactNode> entry : contactLinkedList.getMap().entrySet()) {
            if(entry.getValue() != null) {
                displayedContacts.put(entry.getKey(), entry.getValue().getData().getName());
            }
        }
    }

    private void createNotBeingUsedContactsMap() {

        for (Map.Entry<String, String> entry : MainInfoHolder.getInstance().getSimpleContacts().entrySet()) {
            if (displayedContacts.get(entry.getKey()) == null) {
                notDisplayedContacts.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Map<String, String> getDisplayedContacts() {
        return displayedContacts;
    }

    public Map<String, String> getNotDisplayedContacts() {
        return notDisplayedContacts;
    }
}
