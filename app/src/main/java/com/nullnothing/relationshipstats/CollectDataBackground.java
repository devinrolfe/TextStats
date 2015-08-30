package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

public class CollectDataBackground extends IntentService {

    private Cursor cur;
    
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
                cur = getContentResolver().query(uriSMSURI, null, null, null, null);
                int counter = 0;
                ArrayList<String> textMessages = new ArrayList<String>();
                while (cur != null && counter < 100) {
                    if (cur.moveToNext()) {
                        textMessages.add("From :" + cur.getString(2) + " : " + cur.getString(cur.getColumnIndex("body")) + "\n");
                    }
                    counter++;
                }
                if(cur != null) cur.close();

                mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE, textMessages);

                // Need to create objects to save contacts info, then sort in array then store in xml file
                // Need to create objects for messages, calls, and then again sort in array? this could take up alot
                // of RAM???? Better solution is to just write to file imedidatily and insert in correct location
                // insertion O(n)
                // small optimization. have one cursor on each inbox and sent and add accordly.
                // also only work on 1 contact at a time to optimize things

                break;
            default:
                break;
        }

    }
}

