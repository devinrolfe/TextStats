package com.nullnothing.relationshipstats;

import com.nullnothing.relationshipstats.DataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.DataStructures.ContactNode;
import com.nullnothing.relationshipstats.Enums.Category;
import com.nullnothing.relationshipstats.Enums.TimePeriod;

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
        int minValue = -1;
        int count = 1;

        MainInfoHolder mMainInfoHolder = MainInfoHolder.getInstance();
        List<String> contactList = mMainInfoHolder.getContactList();
        HashMapContactInfoHolder contacts = mMainInfoHolder.getContacts();

        // Loop through contactList to get contacts that fit the given requirements

        for(String contactId : contactList) {

            ContactInfoHolder contact = mMainInfoHolder.getContacts().get(contactId).getValue();

            int value = getValue(contact, category, timePeriod);

            if(value > minValue && count < top) {
                addContactToList(contact);
                count++;
                minValue = value;
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
        result.add(new ContactNode(contact));
    }

}
