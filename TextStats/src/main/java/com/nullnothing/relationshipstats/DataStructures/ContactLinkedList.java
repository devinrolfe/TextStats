package com.nullnothing.relationshipstats.DataStructures;


import com.nullnothing.relationshipstats.Enums.Category;
import com.nullnothing.relationshipstats.Enums.TimePeriod;

public class ContactLinkedList {

    private ContactNode head;
    private int size;
    private Category category;
    private TimePeriod timePeriod;

    private ContactLinkedList() {
        throw new AssertionError();
    }

    public ContactLinkedList(int size, Category category, TimePeriod timePeriod) {
        this.size = size;
    }

    public ContactNode getHead() { return head; }


    public void add(ContactNode node) {

        if (head == null) {
            head = node;
        }

        int count = 0;
        boolean inserted = false;
        ContactNode current = head;
        ContactNode prev = null;

        for (count = 0; count < size; count++) {
            if (node.getData().getTextCount(category, timePeriod) > current.getData().getTextCount(category, timePeriod)) {
                prev.next = node;
                node.next = current;
                inserted = true;
            }
            prev = current;
            current = current.next;
        }

        // Check if size of list is not over size, if it is then remove last element
        while(count < size && current != null) {
            prev = current;
            current = current.next;
            count++;
        }
        if (inserted && count >= (size - 1)) {
            prev.next = null;
        }
    }
}
