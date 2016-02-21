package com.nullnothing.relationshipstats.requests;

import android.content.Intent;

import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.Constants;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;

public class IntentHelperFiller {


    public static void FillRequestInIntent(Intent intent, Request request) {
        if (request instanceof GraphChangeRequest) {
            GraphChangeRequest graphChangeRequest = (GraphChangeRequest) request;
            intent.putExtra(Constants.EXTENDED_DATA_NUM_CONTACTS, graphChangeRequest.getNumContactToGraph());
            intent.putExtra(Constants.EXTENDED_DATA_CATEGORY, graphChangeRequest.getCategory().toString());
            intent.putExtra(Constants.EXTENDED_DATA_INTERVAL, graphChangeRequest.getInterval().toString());
            intent.putExtra(Constants.EXTENDED_DATA_PERIOD, graphChangeRequest.getPeriod().toString());
            intent.putExtra(Constants.EXTENDED_DATA_DISABLE_LEGEND, graphChangeRequest.getDisableLegend());
        }
    }

}
