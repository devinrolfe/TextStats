package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


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
        CollectData.getInstance(getContentResolver());
        CollectData.getInstance().getAllContacts();
    }

    private void collectTextMessages() {

        long startTime = System.currentTimeMillis();
        Log.d("collectTextMessages", "START " + startTime);

        CollectData.getInstance(getContentResolver());

        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new InboxThread());
        threads[0].start();
        threads[1] = new Thread(new SentThread());
        threads[1].start();

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch(InterruptedException e) {

            }
        }

        Log.d("collectTextMessages", "END " + (System.currentTimeMillis() - startTime));

        ContactInfoHolder thing = MainInfoHolder.getInstance().getContacts().get("49").getValue();
        Log.d(thing.getName(), "" + thing.getTextReceived());
        Log.d(thing.getName(), "" + thing.getTextSent());
    }

}



