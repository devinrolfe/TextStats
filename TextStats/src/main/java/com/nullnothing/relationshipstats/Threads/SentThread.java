package com.nullnothing.relationshipstats.threads;

import android.net.Uri;
import android.util.Log;

import com.nullnothing.relationshipstats.backgroundProcessing.CollectData;

public class SentThread implements Runnable {

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        Log.d("Sent Thread", "START " + startTime);
        Uri uriSMSURI = Uri.parse("content://sms/sent");
        CollectData.getInstance().getMessages(uriSMSURI, true);
        Log.d("Sent Thread", "END " + (System.currentTimeMillis() - startTime));
    }
}
