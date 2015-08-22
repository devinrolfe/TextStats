package com.nullnothing.relationshipstats;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Allows the background thread to communicate with the activity
 * Class acts as the local broadcast manager
 */
public class BroadcastNotifier {

    private LocalBroadcastManager mBroadcaster;

    public BroadcastNotifier(Context context) {
        mBroadcaster = LocalBroadcastManager.getInstance(context);
    }

    public void broadcastIntentWithState(int status) {
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);

        localIntent.putExtra(Constants.EXTENDED_DATA_STATUS, status);
        localIntent.addCategory(Intent.CATEGORY_DEFAULT);

        mBroadcaster.sendBroadcast(localIntent);
    }

    public void broadcastIntentWithTexTMessage(String textMessage) {
        Intent localIntent = new Intent(Constants.ACTION_TEXT_MESSAGE);

        localIntent.putExtra(Constants.EXTENDED_DATA_TEXT, textMessage);
        localIntent.addCategory(Intent.CATEGORY_DEFAULT);

        mBroadcaster.sendBroadcast(localIntent);
    }


    //TODO:: don't know if this method should be implemented
    //public notifyProgress(String logData)





}

