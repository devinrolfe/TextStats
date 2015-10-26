package com.nullnothing.relationshipstats;

import android.net.Uri;
import android.util.Log;

/**
 * Created by johndoe on 2015-10-26.
 */
public class InboxThread implements Runnable {

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        Log.d("Inbox Thread", "START " + startTime);
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        CollectData.getInstance().getMessages(uriSMSURI, false);
        Log.d("Inbox Thread", "END " + (System.currentTimeMillis() - startTime));
    }
}
