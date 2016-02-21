package com.nullnothing.relationshipstats.builders;

import android.support.v4.app.FragmentActivity;

import com.nullnothing.relationshipstats.enumsOrConstants.BuilderName;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;

public class GraphChangeRequestBuilder implements Builder {
    // Default values
    private int numContactToGraph;
    private Category category;
    private TimeInterval interval;
    private TimePeriod period;
    private boolean toggleLegend;

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

    public GraphChangeRequestBuilder toggleLegend() {
        this.toggleLegend = true;
        return this;
    }

    public GraphChangeRequest build() {
        return new GraphChangeRequest(this);
    }

    @Override
    public BuilderName getName() {
        return BuilderName.GRAPH_CHANGE_REQUEST_BUILDER;
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

    public boolean getToggleLegend() { return toggleLegend; }

}
