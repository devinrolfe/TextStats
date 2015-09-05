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

                mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE, textMessages);


                ContentResolver cr = getContentResolver();
                cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if (cur.getCount() > 0) {
                    ContactInfoHolder contactInfo;
                    ArrayList<String> allContactNumbers;
                    while (cur.moveToNext()) {
                        allContactNumbers = new ArrayList<String>();

                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String raw_contact_id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));

                        if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                            while (pCur.moveToNext()) {
                                String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                allContactNumbers.add(contactNumber);
                                break;
                            }
                            pCur.close();
                            contactInfo = new ContactInfoHolder(name, allContactNumbers, raw_contact_id);
                            MainInfoHolder.getInstance().addContact(raw_contact_id, contactInfo);
                        }
                    }
                }
                if(cur != null) cur.close();


                Log.d("BACKGROUND_THREAD", "DONE");

                break;
            default:
                break;
        }

    }
}

