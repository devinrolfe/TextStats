package com.nullnothing.relationshipstats.builders;

import android.support.v4.app.FragmentActivity;

import com.nullnothing.relationshipstats.enumsOrConstants.BuilderName;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;

public class GraphChangeRequestBuilder implements Builder {
    // Default values
    private int numContactToGraph = 5;
    private Category category = Category.RECEIVEDMSG;
    private TimeInterval interval = TimeInterval.MONTH;
    private TimePeriod period = TimePeriod.ALL_TIME;
    private FragmentActivity fragmentActivity;

    public GraphChangeRequestBuilder() {
    }

    public GraphChangeRequestBuilder numberOfContactsToGraph(int numContactToGraph) {
        this.numContactToGraph = numContactToGraph;
        return this;
    }

    public GraphChangeRequestBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public GraphChangeRequestBuilder interval(TimeInterval interval) {
        this.interval = interval;
        return this;
    }

    public GraphChangeRequestBuilder period(TimePeriod period) {
        this.period = period;
        return this;
    }

    public GraphChangeRequest build() {
        return new GraphChangeRequest(this);
    }

    @Override
    public BuilderName getName() {
        return BuilderName.GRAPH_CHANGE_REQUEST_BUILDER;
    }

    public FragmentActivity getFragmentActivity() { return fragmentActivity; }

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
}
