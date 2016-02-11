package com.nullnothing.relationshipstats;

import com.nullnothing.relationshipstats.dataStorageObjects.ContactInfoHolder;
import com.nullnothing.relationshipstats.dataStorageObjects.HashMapContactInfoHolder;
import com.nullnothing.relationshipstats.dataStorageObjects.MainInfoHolder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;

import java.util.List;

public class DataParserUtil {

    private DataParserUtil() {
        throw new AssertionError();
    }

    private static ContactLinkedList result;

    /*
    Get top contacts that fit the requirements and return an array
     */
    public static ContactLinkedList getTopContactsInCategory(int top, Category category, TimePeriod timePeriod) {


        result  = new ContactLinkedList(top, category, timePeriod);
        int count = 0;

        MainInfoHolder mMainInfoHolder = MainInfoHolder.getInstance();
        List<String> contactList = mMainInfoHolder.getContactList();
        HashMapContactInfoHolder contacts = mMainInfoHolder.getContacts();

        // Loop through contactList to get contacts that fit the given requirements

        for(String contactId : contactList) {

            ContactInfoHolder contact = mMainInfoHolder.getContacts().get(contactId).getValue();

            int value = getValue(contact, category, timePeriod);

            if(value > result.getEndValue() || count < top) {
                addContactToList(contact);
                count++;
            }
        }
        return result;
    }

    /*
    Return whether contact upholds conditions
     */
    private static int getValue(ContactInfoHolder contact, Category category, TimePeriod timePeriod) {
        return contact.getTextCount(category, timePeriod);
    }

    private static void addContactToList(ContactInfoHolder contact) {
        result.add(contact);
    }
}
