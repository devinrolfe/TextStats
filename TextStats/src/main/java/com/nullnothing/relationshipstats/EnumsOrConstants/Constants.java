package com.nullnothing.relationshipstats.enumsOrConstants;

public final class Constants {

    // Define a custom intend action
    public static final String BROADCAST_ACTION = "com.nullnothing.relationshipstats.BROADCAST";
    // Defines the key for the status "extra" in an intent
    public static final String EXTENDED_DATA_STATUS = "com.nullnothing.relationshipstats.STATUS";
    public static final String EXTENDED_DATA_TEXTLIST = "com.nullnothing.relationshipstats.TEXTLIST";



    // For Graph/Card
    public static final String EXTENDED_DATA_NUM_CONTACTS= "com.nullnothing.relationshipstats.numContacts";
    public static final String EXTENDED_DATA_CATEGORY= "com.nullnothing.relationshipstats.category";
    public static final String EXTENDED_DATA_INTERVAL= "com.nullnothing.relationshipstats.interval";
    public static final String EXTENDED_DATA_PERIOD = "com.nullnothing.relationshipstats.period";


    // Status values to broadcast to the Activity

    // The download is starting
    public static final int STATE_ACTION_STARTED = 0;
    // The background thread is connecting to the RSS feed
    public static final int STATE_ACTION_CONNECTING = 1;
    // The background thread is parsing the RSS feed
    public static final int STATE_ACTION_PARSING = 2;
    // The background thread is writing data to the content provider
    public static final int STATE_ACTION_WRITING = 3;
    // The background thread is done
    public static final int STATE_ACTION_COMPLETE = 4;
    // The background thread is doing logging
    public static final int STATE_LOG = -1;
    //
    public static final int CHANGE_GRAPH_REQUEST = 5;
    public static final int CHANGE_CARD_REQUEST = 6;

    public static long lastReceivedTimestamp;
    public static long lastSentTimestamp;
}
