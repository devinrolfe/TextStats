package com.nullnothing.relationshipstats.util;

import android.support.design.widget.NavigationView;
import android.util.Log;

import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.NavigationMenuUI;
import com.nullnothing.relationshipstats.enumsOrConstants.Others;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.fragments.FragmentInterface;
import com.nullnothing.relationshipstats.fragments.GraphFragment;
import com.nullnothing.relationshipstats.navigationMenu.ExpandedMenuModel;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;
import com.nullnothing.relationshipstats.requests.Request;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavigationMenuChangeHelper {

    private NavigationMenuChangeHelper() {
        throw new AssertionError();
    }

    public static void NavigationMenuChangeHelper(FragmentInterface fragment,
                                                  HashMap<ExpandedMenuModel, List<String>> listDataChild,
                                                  List<ExpandedMenuModel> listDataHeader,
                                                  int groupPosition,
                                                  int childPosition) {

            if (fragment instanceof GraphFragment) {
                changeGraphFragment(
                        listDataHeader.get(groupPosition).getIconName(),
                        listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString());
            }

    }
    private static void changeGraphFragment(String changeGraphVariableName,
                                            String changeGraphValueName) {

        // First we extract the value that is changing, then we get the prev request and
        // extract all the values that did not change
        GraphChangeRequestBuilder builder = new GraphChangeRequestBuilder();

        setGraphChangedValue(changeGraphVariableName, changeGraphValueName, builder);
        setGraphNotChangedValues(changeGraphValueName, changeGraphValueName, builder);
        builder.build().executeRequest();
    }

    private static void setGraphChangedValue(String changeGraphVariableName,
                                             String changeGraphValueName,
                                             GraphChangeRequestBuilder builder) {

        if (changeGraphVariableName.equals(NavigationMenuUI.Top_NUM_CONTACTS.toString())) {
            builder.numberOfContactsToGraph(Integer.parseInt(changeGraphValueName));
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

    private static void setGraphNotChangedValues(String changeGraphVariableName,
                                                 String changeGraphValueName,
                                                 GraphChangeRequestBuilder builder) {

        ArrayList<GraphChangeRequest> prevRequests =  GraphChangeRequest.prevRequests;
        int size = GraphChangeRequest.prevRequests.size();

        if (size < 1) {
            return;
        }
        // check what values in builder are not set, then set those values using prev requests
        if (builder.getNumContactToGraph() < 1) {
            builder.numberOfContactsToGraph(prevRequests.get(size - 1).getNumContactToGraph());
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
