package com.nullnothing.relationshipstats.DataStructures;

import com.nullnothing.relationshipstats.ContactInfoHolder;

public class ContactNode {

    private ContactInfoHolder data;
    ContactNode next;

    public ContactNode(ContactInfoHolder contact) {
        this.data = contact;
        this.next = null;
    }

    public ContactInfoHolder getData() { return this.data; }

}
