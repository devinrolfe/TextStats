package com.nullnothing.relationshipstats.DataStructures;


import com.nullnothing.relationshipstats.EnumsOrConstants.Category;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactLinkedList {

    private ContactNode head;
    private ContactNode end;
    private int size;
    private Category category;
    private TimePeriod timePeriod;

    private List<String> xValues;
    private Map<String, ContactNode> quickAccessMap = new DefaultHashMap<>(null); // rawID -> ContactNode

    private ContactLinkedList() {
        throw new AssertionError();
    }

    public ContactLinkedList(int size, Category category, TimePeriod timePeriod) {
        this.size = size;
        this.category = category;
        this.timePeriod = timePeriod;
    }

    public Category getCategory() { return category; }

    public void setXValues(List<String> xValuesList) { xValues = xValuesList; }
    public List getXValues() { return xValues; }

    public ContactNode getHead() { return head; }

    public int getEndValue() {
        if (end == null) {
            return -1;
        }
        return end.getData().getTextCount(category, timePeriod);
    }

    // Only used when first initializing so that size limit is not violated.
    public void add(ContactNode node) {
        add(node, false);
    }

    public void add(ContactNode node, boolean allowSizeViolation) {
        if(head == null) {
            head = node;
            end = node;
        }
        else {
            ContactNode cur = head;
            ContactNode prev = null;
            boolean inserted = false;
            int count = 0;

            while(cur != null) {
                count++;
                if(cur.getData().getTextCount(category, timePeriod) <= node.getData().getTextCount(category, timePeriod)) {
                    inserted = true;

                    quickAccessMap.put(node.getData().getId(),node);

                    if(cur == head) {
                        head = node;
                        prev = head;
                        node.next = cur;
                        end = cur;
                    }
                    else{
                        prev.next = node;
                        node.next = cur;
                    }
                    break;
                }
                prev = cur;
                cur = cur.next;
            }

            cur = head;
            count = 0;
            if (!allowSizeViolation) {
                while (cur != null) {
                    count++;
                    if (count >= size) {
                        if(cur.next != null) quickAccessMap.put(cur.next.getData().getId(), null); // erase from cache
                        cur.next = null;
                        break;
                    }
                    prev = cur;
                    cur = cur.next;
                }
            }

            end = prev;

            if(!inserted) {
                prev.next = node;
                end = node;
                quickAccessMap.put(node.getData().getId(),node);
            }

        }

    }



}
