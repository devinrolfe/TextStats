package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CollectDataBackground extends IntentService {

    private static HashMap<String, String> fromToId = new HashMap<String, String>();

    private BroadcastNotifier mBroadcaster = new BroadcastNotifier(this);

    public CollectDataBackground() {
        super("CollectDataBackground");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();
        // Do work here based on what dataString is...
        //use mBroadcaster to send status intents
        int status;
        switch (dataString) {
            case "InitialCollectionSetup":
                Uri uriSMSURI = Uri.parse("content://sms/inbox");
                Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
                int counter = 0;
                ArrayList<String> textMessages = new ArrayList<String>();
                while (cur != null && counter < 100) {
                    if (cur.moveToNext()) {
                        textMessages.add("From :" + cur.getString(2) + " : " +
                                cur.getString(cur.getColumnIndex("body")) + "\nid: " +
                                cur.getString(cur.getColumnIndex(Telephony.TextBasedSmsColumns.PERSON)));
                    }
                    counter++;
                }
                if(cur != null) cur.close();

                getAllContacts();
                collectTextMessages();

                mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE, textMessages);

                Log.d("BACKGROUND_THREAD", "DONE");

                break;
            default:
                break;
        }

    }

    public void getAllContacts() {
        ContactInfoHolder contactInfoHolder;
        HashMap<String, ContactInfoHolder> contactMap = new HashMap<String, ContactInfoHolder>();

        String contactId;
        String contactName;
        String rawContactId;
        ArrayList<String> contactPhones;

        ContentResolver cr = getContentResolver();
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

    private void collectTextMessages() {

        long startTime = System.currentTimeMillis();
        Log.d("collectTextMessages", "START " + startTime);

        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        getMessages(uriSMSURI, false);
        uriSMSURI = Uri.parse("content://sms/sent");
        getMessages(uriSMSURI, true);

        Log.d("collectTextMessages", "END " + (System.currentTimeMillis() - startTime));
    }

    private void getMessages(Uri uriSMSURI, boolean isASent) {
        Cursor cur = null;

        int stopTest = 0;

        String id;
        String from;
        String message;
        long timestamp;
        TextMessage msg;

        for(int whichRun=0; whichRun < 2; whichRun++) {

            cur = getContentResolver().query(uriSMSURI, null, null, null, null);
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
        int stop = 1;
        Log.d("SENT", "" + stopTest);
    }

    public String getContactIdFromNumber(String phoneNumber) {
        String contactId = "";
        String rawContactId = "";

        ContentResolver contentResolver = getContentResolver();

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
            Cursor c = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
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

}



