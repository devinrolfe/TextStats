package com.nullnothing.relationshipstats;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;

import com.nullnothing.relationshipstats.Enums.TimePeriod;
import com.nullnothing.relationshipstats.TextMessageDecorator.AllTimeDecorator;
import com.nullnothing.relationshipstats.TextMessageDecorator.DayDecorator;
import com.nullnothing.relationshipstats.TextMessageDecorator.MonthDecorator;
import com.nullnothing.relationshipstats.TextMessageDecorator.WeekDecorator;
import com.nullnothing.relationshipstats.TextMessageDecorator.YearDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollectData {

    private static CollectData mCollectData;

    private static HashMap<String, String> fromToId = new HashMap<String, String>();
    private ContentResolver mContentResolver;


    public CollectData(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }

    public static CollectData getInstance() {

        if (mCollectData == null) {
            return null;
        }
        return mCollectData;
    }

    public static CollectData getInstance(ContentResolver mContentResolver) {

        if (mCollectData == null) {
            mCollectData = new CollectData(mContentResolver);
        }
        else {
            mCollectData.mContentResolver = mContentResolver;
        }
        return mCollectData;
    }

    public void getAllContacts() {
        ContactInfoHolder contactInfoHolder;
        HashMap<String, ContactInfoHolder> contactMap = new HashMap<String, ContactInfoHolder>();

        String contactId;
        String contactName;
        String rawContactId;
        ArrayList<String> contactPhones;

        ContentResolver cr = mContentResolver;
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext()){
            contactId = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            contactName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            rawContactId = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
            contactPhones=getAllNumbersForContact(cr, contactId, rawContactId);
            contactInfoHolder = new ContactInfoHolder(rawContactId, contactName, contactPhones);
            if(contactMap.get(rawContactId) == null) contactMap.put(rawContactId, contactInfoHolder);
        }
        phones.close();

        MainInfoHolder.contactCount = contactMap.size();

        for (Map.Entry<String, ContactInfoHolder> entry : contactMap.entrySet()) {
            MainInfoHolder.getInstance().addContact(entry.getValue());
        }
    }

    public ArrayList<String> getAllNumbersForContact(ContentResolver cr, String contactId, String rawContactId) {
        ArrayList<String> phoneNumbers= new ArrayList<String>();
        Cursor numbers = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
        while (numbers.moveToNext()) {
            String phoneNumber = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumbers.add(phoneNumber);
            if(fromToId.get(phoneNumber) == null) fromToId.put(phoneNumber, rawContactId);
        }
        numbers.close();

        return phoneNumbers;
    }

    public void getMessages(Uri uriSMSURI, boolean isASent) {
        Cursor cur = null;

        String id;
        String from;
        String message;
        long timestamp;
        TextMessage msg;

        for(int whichRun=0; whichRun < 2; whichRun++) {

            cur = mContentResolver.query(uriSMSURI, null, null, null, null);
            while (cur.moveToNext()) {
                id = cur.getString(cur.getColumnIndex(Telephony.TextBasedSmsColumns.PERSON));
                from = cur.getString(cur.getColumnIndex("address"));
                message = cur.getString(cur.getColumnIndex("body"));
                timestamp = Long.parseLong(cur.getString(cur.getColumnIndex("date")));

                switch (whichRun) {
                    case 0: // first run to create hashMap mapping
                        if (id == null) {
                            if (fromToId.get(from) == null) {
                                id = getContactIdFromNumber(from);
                                fromToId.put(from, id);
                            }
                        }
                        else if (id != null && from != null) {
                            if (fromToId.get(from) == null) {
                                fromToId.put(from, id);
                            }
                        }
                        break;
                    case 1: // second run to properly add
                        msg = isASent ? new SentMessage(timestamp, message) : new ReceivedMessage(timestamp, message);

                        // TODO : Decorate with proper Time Period
                        msg = decorateMessage(msg);


                        if (from != null) {
                            if (id == null) {
                                id = fromToId.get(from);
                            }
                            if ( id != null) {
                                MainInfoHolder.getInstance().addTextMessage(id, from, msg);
                            }
                            // if no id is found, then the text message does not have
                            // an associated contact to it.
                        }
                        break;
                }
            }
            if(cur != null) cur.close();
        }
    }

    public String getContactIdFromNumber(String phoneNumber) {
        String contactId = "";
        String rawContactId = "";

        ContentResolver contentResolver = mContentResolver;

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));

        String[] projection = new String[] {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);

        if(cursor!=null) {
            while(cursor.moveToNext()){
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
                break;
            }
            cursor.close();
        }
        if (!contactId.equals("")) {
            Cursor c = mContentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                    new String[]{ContactsContract.RawContacts._ID},
                    ContactsContract.RawContacts.CONTACT_ID + "=?",
                    new String[]{String.valueOf(contactId)}, null);
            try {
                if (c.moveToFirst()) {
                    rawContactId = c.getString(0);
                }
            } finally {
                c.close();
            }
        }
        return rawContactId;
    }

    public TextMessage decorateMessage(TextMessage mTextMessage) {

        long timestamp = mTextMessage.getTimestamp();

        TimePeriod period = CalandarHelper.INSTANCE.howOld(timestamp);

        if(period.equals(TimePeriod.DAY)) {
            return new DayDecorator(mTextMessage);
        }
        else if(period.equals(TimePeriod.WEEK)) {
            return new WeekDecorator(mTextMessage);
        }
        else if(period.equals(TimePeriod.MONTH)) {
            return new MonthDecorator(mTextMessage);
        }
        else if(period.equals(TimePeriod.YEAR)) {
            return new YearDecorator(mTextMessage);
        }
        else{
            return new AllTimeDecorator(mTextMessage);
        }
    }

}
