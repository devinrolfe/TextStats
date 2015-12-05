package com.nullnothing.relationshipstats;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


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
                getAllContacts();
                collectTextMessages();

                mBroadcaster.broadcastIntentWithState(Constants.STATE_ACTION_COMPLETE);

                Log.d("BACKGROUND_THREAD", "DONE");
                TextStatsActivity.backgroundCollectionDone = true;
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
    }

}



