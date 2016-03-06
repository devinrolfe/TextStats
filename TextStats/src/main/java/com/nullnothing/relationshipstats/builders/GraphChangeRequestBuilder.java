package com.nullnothing.relationshipstats.builders;

import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.enumsOrConstants.BuilderName;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;

public class GraphChangeRequestBuilder implements Builder {
    // Default values
    private ContactLinkedList contactsToGraph;
    private Category category;
    private TimeInterval interval;
    private TimePeriod period;
    private boolean toggleLegend;

    public GraphChangeRequestBuilder() {
    }

    public GraphChangeRequestBuilder contactsToGraph(ContactLinkedList contactsToGraph) {
        this.contactsToGraph = contactsToGraph;
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

    public boolean getToggleLegend() {
        return toggleLegend;
    }

}
