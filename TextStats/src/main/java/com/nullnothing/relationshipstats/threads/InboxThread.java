package com.nullnothing.relationshipstats.threads;

import android.net.Uri;
import android.util.Log;

import com.nullnothing.relationshipstats.backgroundProcessing.CollectData;

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
