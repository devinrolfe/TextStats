package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.Intent;

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

    }






}


