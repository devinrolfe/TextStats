package com.nullnothing.relationshipstats.requests;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

import com.nullnothing.relationshipstats.builders.Builder;
import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.handlers.GraphHandler;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GraphChangeRequest implements Request {

    // TODO : Have a static variable of request which represents the previous request, so
    // that we can create new requests using the values that did not change from the previous
    // request
    public static ArrayList<GraphChangeRequest> prevRequests = new ArrayList<>();

    private int numContactToGraph;
    private Category category;
    private TimeInterval interval;
    private TimePeriod period;
    private boolean disableLegend;

    public GraphChangeRequest(Builder builder) {
        if(!(builder instanceof GraphChangeRequestBuilder)) {
            throw new InvalidParameterException();
        }
        GraphChangeRequestBuilder graphChangeRequestBuilder = (GraphChangeRequestBuilder) builder;

        numContactToGraph = graphChangeRequestBuilder.getNumContactToGraph();
        category = graphChangeRequestBuilder.getCategory();
        interval = graphChangeRequestBuilder.getInterval();
        period = graphChangeRequestBuilder.getPeriod();

        if (graphChangeRequestBuilder.getToggleLegend() && prevRequests.size() > 0) {
            disableLegend = prevRequests.get(prevRequests.size() - 1).disableLegend ? false : true;
        }

        if(prevRequests.size() > 1) prevRequests.remove(0);
        prevRequests.add(this);
    }

    public void executeRequest() {
        new GraphHandler().handleRequest(this);
    }

    public boolean isPreviousRequest() {
        return prevRequests.size() > 0;
    }

    public Request getPreviousRequest() {
        if(prevRequests.size() > 1) return prevRequests.get(prevRequests.size() - 1);
        return null;
    }

    public int getNumContactToGraph() {
        return numContactToGraph;
    }

    public Category getCategory() {
        return category;
    }

    public TimeInterval getInterval() {
        return interval;
    }

    public TimePeriod getPeriod() {
        return period;
    }

    public boolean getDisableLegend() { return disableLegend; }

}
