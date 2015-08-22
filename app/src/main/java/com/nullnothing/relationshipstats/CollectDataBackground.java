package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class CollectDataBackground extends IntentService {

    private Cursor cur1;


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

        if (dataString.equals("InitialCollectionSetup")) {
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            cur1 = getContentResolver().query(uriSMSURI, null, null, null, null);

            mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE);
        }
        else if (dataString.equals("getNextText")) {

            if (cur1 != null) {
                String sms = "";

                if (cur1.moveToNext()) {
                    sms += "From :" + cur1.getString(2) + " : " + cur1.getString(cur1.getColumnIndex("body")) + "\n";
                    //return text message to fragment
                }
                mBroadcaster.broadcastIntentWithTexTMessage(sms);
            }
        }
        else{
            // should do something about error message returning
        }





    }






}


