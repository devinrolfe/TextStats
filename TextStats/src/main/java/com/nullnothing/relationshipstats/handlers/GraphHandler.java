package com.nullnothing.relationshipstats.handlers;

import com.nullnothing.relationshipstats.BroadcastNotifier;
import com.nullnothing.relationshipstats.R;
import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.enumsOrConstants.Constants;
import com.nullnothing.relationshipstats.fragments.FragmentInterface;
import com.nullnothing.relationshipstats.graphing.LineDataSetCreator;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;
import com.nullnothing.relationshipstats.requests.Request;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class GraphHandler extends IntentService implements Handler {

    private BroadcastNotifier mBroadcaster = new BroadcastNotifier(this);

    public GraphHandler() {
        super("GraphHandler");
    }

    public void handleRequest(Request request) {

        if(!(request instanceof GraphChangeRequest)) {
            throw new InvalidParameterException();
        }

        mBroadcaster.broadcastIntentWithState(Constants.CHANGE_GRAPH_REQUEST, request);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Don't do anything here, this class does nothing with intents sent to it
    }
}
