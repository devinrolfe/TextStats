package com.nullnothing.relationshipstats.builders;

import com.nullnothing.relationshipstats.dataPoint.DataParserUtil;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.NavigationMenuUI;
import com.nullnothing.relationshipstats.enumsOrConstants.Others;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;

import java.util.ArrayList;

public class GraphBuilderHelper {


    private GraphBuilderHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void setGraphChangedValue(String changeGraphVariableName,
                                             String changeGraphValueName,
                                             GraphChangeRequestBuilder builder) {

        if (changeGraphVariableName.equals(NavigationMenuUI.Top_NUM_CONTACTS.toString())) {

            ContactLinkedList contactsToGraph;
            ArrayList<GraphChangeRequest> prevRequest = GraphChangeRequest.prevRequests;

            if (prevRequest.size() > 0) {
                contactsToGraph = DataParserUtil.getTopContactsInCategory(
                        Integer.parseInt(changeGraphValueName),
                        prevRequest.get(prevRequest.size() - 1).getCategory(),
                        prevRequest.get(prevRequest.size() - 1).getPeriod());
            }
            else {
                contactsToGraph = DataParserUtil.getTopContactsInCategory(3, Category.RECEIVEDMSG, TimePeriod.ALL_TIME);
            }
            builder.contactsToGraph(contactsToGraph);
        }
        else if (changeGraphVariableName.equals(NavigationMenuUI.MESSAGE_TYPES.toString())) {
            builder.category(Category.getValueOf(changeGraphValueName));
        }
        else if (changeGraphVariableName.equals(NavigationMenuUI.INTERVALS.toString())) {
            builder.interval(TimeInterval.getValueOf(changeGraphValueName));
        }
        else if (changeGraphVariableName.equals(NavigationMenuUI.PERIOD.toString())) {
            builder.period(TimePeriod.getValueOf(changeGraphValueName));
        }
        else if (changeGraphVariableName.equals(NavigationMenuUI.OTHER.toString())) {
            if (changeGraphValueName.equals(Others.ENABLE_DISABLE_LEGEND.toString())) {
                builder.toggleLegend();
            }
        }
    }

    public static void setGraphNotChangedValues(GraphChangeRequestBuilder builder) {

        ArrayList<GraphChangeRequest> prevRequests =  GraphChangeRequest.prevRequests;
        int size = GraphChangeRequest.prevRequests.size();

        if (size < 1) {
            return;
        }
        // check what values in builder are not set, then set those values using prev requests
        if (builder.getContactsToGraph() == null) {
            ArrayList<GraphChangeRequest> prevRequest = GraphChangeRequest.prevRequests;

            if (prevRequest.size() > 0) {
                builder.contactsToGraph(prevRequest.get(prevRequest.size() - 1).getContactsToGraph());

            }
        }
        if (builder.getCategory() == null) {
            builder.category(prevRequests.get(size - 1).getCategory());
        }
        if (builder.getInterval() == null) {
            builder.interval(prevRequests.get(size - 1).getInterval());
        }
        if (builder.getPeriod() == null) {
            builder.period(prevRequests.get(size - 1).getPeriod());
        }
    }


}
