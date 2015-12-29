package com.nullnothing.relationshipstats.DataStructures;


import com.nullnothing.relationshipstats.EnumsOrConstants.Category;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;

public class ContactLinkedList {

    private ContactNode head;
    private ContactNode end;
    private int size;
    private Category category;
    private TimePeriod timePeriod;

    private ContactLinkedList() {
        throw new AssertionError();
    }

    public ContactLinkedList(int size, Category category, TimePeriod timePeriod) {
        this.size = size;
        this.category = category;
        this.timePeriod = timePeriod;
    }

    public ContactNode getHead() { return head; }

    public int getEndValue() {
        if (end == null) {
            return -1;
        }
        return end.getData().getTextCount(category, timePeriod);
    }

    // TODO : NEED TO BE REWORKED
    public void add(ContactNode node) {

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

            while(cur != null) {
                count++;
                if(count >= size) {
                    cur.next = null;
                    break;
                }
                prev = cur;
                cur = cur.next;
            }
            end = prev;

            if(!inserted) {
                prev.next = node;
                end = node;
            }

        }



    }
}
