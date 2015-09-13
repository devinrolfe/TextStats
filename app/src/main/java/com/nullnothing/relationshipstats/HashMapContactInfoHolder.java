package com.nullnothing.relationshipstats;


public class HashMapContactInfoHolder {

    private static int SIZE;
    private Entry table[];

    public HashMapContactInfoHolder(int size){
        SIZE = size;
        table = new Entry[SIZE*2];
    }

    class Entry {
        private final String key;
        private ContactInfoHolder value;
        Entry next;

        Entry(String k, ContactInfoHolder v) {
            this.key = k;
            this.value = v;
        }

        public ContactInfoHolder getValue() {
            return this.value;
        }

        public void setValue(ContactInfoHolder value) {
            this.value = value;
        }

        public void setValue(TextMessage value) {
            this.value.addTextMessage(value);
        }

        public String getKey() {
            return key;
        }
    }

    public Entry get(String k) {
        int hash = Math.abs(k.hashCode() % SIZE);
        Entry e = table[hash];

        // if bucket is found then traverse through the linked list and
        // see if element is present
        while(e != null) {
            if(e.getKey().equals(k)) {
                return e;
            }
            e = e.next;
        }
        return null;
    }

    public void put(String k, ContactInfoHolder v) {
        int hash = Math.abs(k.hashCode() % SIZE); // NEED TO CHECK absolute hash value
        Entry e = table[hash];
        if(e != null) {
            // it means we are trying to insert duplicate
            // key-value pair, hence overwrite the current
            // pair with the old pair
            if(e.getKey().equals(k)) {
                e.setValue(v);
            } else {
                // traverse to the end of the list and insert new element
                // in the same bucket
                while(e.next != null) {
                    e = e.next;
                }
                Entry entryInOldBucket = new Entry(k, v);
                e.next = entryInOldBucket;
            }
        } else {
            // new element in the map, hence creating new bucket
            Entry entryInNewBucket = new Entry(k, v);
            table[hash] = entryInNewBucket;
        }
    }
    // If contactInfoHolder doesn't isn't then nothing is done
    public void put(String k, TextMessage v) {
        int hash = Math.abs(k.hashCode() % SIZE);
        Entry e = table[hash];
        if(e != null) {
            // it means we are trying to insert duplicate
            // key-value pair, hence overwrite the current
            // pair with the old pair
            if(e.getKey().equals(k)) {
                e.setValue(v);
            } else {
                // traverse to the end of the list and insert new element
                // in the same bucket
                while(e.next != null) {

                    if(e.getKey().equals(k)){
                        e.setValue(v);
                    }
                    e = e.next;
                }
            }
        }
    }

}