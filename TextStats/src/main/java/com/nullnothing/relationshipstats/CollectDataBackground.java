package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.util.ArrayList;
import java.util.HashMap;


public class CollectDataBackground extends IntentService {

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

                collectContacts();
                collectTextMessages();

                mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE, textMessages);

                Log.d("BACKGROUND_THREAD", "DONE");

                break;
            default:
                break;
        }

    }

    private void collectContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        ContactInfoHolder contactInfoHolder;
        ArrayList<ContactInfoHolder> contactList = new ArrayList<ContactInfoHolder>();

        if (cur.getCount() > 0) {
            ArrayList<String> allContactNumbers;
            String id;
            String name;
            String raw_contact_id;
            String contactNumber;
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

            while (cur.moveToNext()) {
                allContactNumbers = new ArrayList<String>();

                id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                raw_contact_id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));

                if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
//                    Log.d("CONTACT", "START - " + name + ", ID - " + raw_contact_id);
                    while (pCur.moveToNext()) {
                        contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        allContactNumbers.add(contactNumber);
                        break;
                    }
//                    Log.d("CONTACT", "END");
                    pCur.close();

                    contactInfoHolder = new ContactInfoHolder(raw_contact_id, name, allContactNumbers);
                    contactList.add(contactInfoHolder);
                }
            }
            MainInfoHolder.contactCount = contactList.size();

            for(ContactInfoHolder tempContactInfoHolder : contactList) {
                MainInfoHolder.getInstance().addContact(tempContactInfoHolder);
            }
        }
        if(cur != null) cur.close();

    }

    private void collectTextMessages() {

        long startTime = System.currentTimeMillis();
        Log.d("collectTextMessages", "START " + startTime);

        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        getMessages(uriSMSURI, false);
        uriSMSURI = Uri.parse("content://sms/sent");
        getMessages(uriSMSURI, true);

        Log.d("collectTextMessages", "END " + (System.currentTimeMillis() - startTime));

        ContactInfoHolder thing = MainInfoHolder.getInstance().getContacts().get("71").getValue();
        Log.d(thing.getName(), "" + thing.getTextReceived());
        Log.d(thing.getName(), "" + thing.getTextSent());
    }

    private void getMessages(Uri uriSMSURI, boolean isASent) {
        Cursor cur = null;

        String id;
        String from;
        String message;
        long timestamp;
        TextMessage msg;
        HashMap<String, String> fromToId = new HashMap<String, String>();

        for(int whichRun=0; whichRun < 2; whichRun++) {

            cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            while (cur.moveToNext()) {
                id = cur.getString(cur.getColumnIndex(Telephony.TextBasedSmsColumns.PERSON));
                from = cur.getString(cur.getColumnIndex("address"));
                message = cur.getString(cur.getColumnIndex("body"));
                timestamp = Long.parseLong(cur.getString(cur.getColumnIndex("date")));

                switch (whichRun) {
                    case 0: // first run to create hashMap mapping
                        if (id != null && from != null) {
                            if (fromToId.get(id) != null) {
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
    }



}



