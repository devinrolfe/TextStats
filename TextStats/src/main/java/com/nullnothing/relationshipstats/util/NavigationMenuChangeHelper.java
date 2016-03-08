package com.nullnothing.relationshipstats.util;

import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.fragments.FragmentInterface;
import com.nullnothing.relationshipstats.fragments.GraphFragment;
import com.nullnothing.relationshipstats.navigationMenu.ExpandedMenuModel;
import com.nullnothing.relationshipstats.builders.GraphBuilderHelper;

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

        GraphBuilderHelper.setGraphChangedValue(changeGraphVariableName, changeGraphValueName, builder);
        GraphBuilderHelper.setGraphNotChangedValues(builder);
        builder.build().executeRequest();
    }

}
