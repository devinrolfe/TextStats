package com.nullnothing.relationshipstats.dataStructures;


import android.os.Parcel;
import android.os.Parcelable;

import com.nullnothing.relationshipstats.dataStorageObjects.ContactInfoHolder;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactLinkedList implements Parcelable {

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
    public int getSize() { return size; }

    public void setXValues(List<String> xValuesList) { xValues = xValuesList; }
    public List<String> getXValues() { return xValues; }

    public ContactNode getHead() { return head; }

    public int getEndValue() {
        if (end == null) {
            return -1;
        }
        return end.getData().getTextCount(category, timePeriod);
    }

    // Only used when first initializing so that size limit is not violated.
    public void add(ContactInfoHolder contact) {
        add(contact, false);
    }

    public void add(ContactInfoHolder contact, boolean allowSizeViolation) {
        ContactNode node = new ContactNode(contact);

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
                        if(cur.next == null) end = cur;
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
            // Inserts at end of list
            if(!inserted) {
                prev.next = node;
                end = node;
                quickAccessMap.put(node.getData().getId(),node);
            }

            // Remove last element in list
            if(!allowSizeViolation && head !=null) {
                count = 0;
                node = head;
                prev = node;
                while(node.next != null) {
                    count++;
                    prev = node;
                    node = node.next;
                }

                if(count >= size) {
                    quickAccessMap.put(node.getData().getId(), null);
                    node = null;
                    prev.next = null;
                    end = prev;
                }
            }
        }

    }
//    ContactNode node = contactLinkedList.getHead();
//
//    List<Thread> threads = new ArrayList();
//
//    while (node != null) {
//        Thread thread = new Thread(new DataPointThread(node, interval, period, category));
//        threads.add(thread);
//        thread.start();
//        node = node.next;
//    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(head);
        dest.writeValue(end);
        dest.writeInt(size);
        dest.writeValue(category);
        dest.writeValue(timePeriod);
        dest.writeList(xValues);
        dest.writeMap(quickAccessMap);
    }

    private ContactLinkedList(Parcel in) {
        head = (ContactNode)in.readValue(ContactNode.class.getClassLoader());
        end = (ContactNode)in.readValue(ContactNode.class.getClassLoader());
        size = (int)in.readInt();
        category = (Category)in.readValue(Category.class.getClassLoader());
        timePeriod = (TimePeriod)in.readValue(TimePeriod.class.getClassLoader());

        xValues = new ArrayList<String>();
        in.readList(xValues, null);

        quickAccessMap = new DefaultHashMap<>(null);
        in.readMap(quickAccessMap, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ContactLinkedList createFromParcel(Parcel in) {
            return new ContactLinkedList(in);
        }

        public ContactLinkedList[] newArray(int size) {
            return new ContactLinkedList[size];
        }
    };
}
