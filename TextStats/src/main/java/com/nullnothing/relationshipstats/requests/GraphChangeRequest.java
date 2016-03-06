package com.nullnothing.relationshipstats.requests;

import com.nullnothing.relationshipstats.builders.Builder;
import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.handlers.GraphHandler;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GraphChangeRequest implements Request {

    public static ArrayList<GraphChangeRequest> prevRequests = new ArrayList<>();

    private ContactLinkedList contactsToGraph;
    private Category category;
    private TimeInterval interval;
    private TimePeriod period;
    private boolean disableLegend;

    public GraphChangeRequest(Builder builder) {
        if(!(builder instanceof GraphChangeRequestBuilder)) {
            throw new InvalidParameterException();
        }
        GraphChangeRequestBuilder graphChangeRequestBuilder = (GraphChangeRequestBuilder) builder;

        contactsToGraph = graphChangeRequestBuilder.getContactsToGraph();
        category = graphChangeRequestBuilder.getCategory();
        interval = graphChangeRequestBuilder.getInterval();
        period = graphChangeRequestBuilder.getPeriod();

        if (graphChangeRequestBuilder.getToggleLegend() && prevRequests.size() > 0) {
            disableLegend = prevRequests.get(prevRequests.size() - 1).disableLegend ? false : true;
        }
        else if (prevRequests.size() > 0) {
            disableLegend = prevRequests.get(prevRequests.size() - 1).disableLegend;
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

    public ContactLinkedList getContactsToGraph() {
        return contactsToGraph;
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
