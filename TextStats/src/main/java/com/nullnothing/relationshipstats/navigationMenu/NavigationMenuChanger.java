package com.nullnothing.relationshipstats.navigationMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationMenuChanger {

    private NavigationMenuChanger(){
        throw new AssertionError();
    }

    public static NavigationMenuHolder prepareListDataForGraphFragment() {
        List<ExpandedMenuModel> listDataHeader = new ArrayList<>();
        HashMap<ExpandedMenuModel, List<String>> listDataChild = new HashMap<>();

        ExpandedMenuModel topContact = new ExpandedMenuModel();
        topContact.setIconName("Top # Of Contacts");
        listDataHeader.add(topContact);

        ExpandedMenuModel messageType = new ExpandedMenuModel();
        messageType.setIconName("Message Types");
        listDataHeader.add(messageType);

        ExpandedMenuModel interval = new ExpandedMenuModel();
        interval.setIconName("Intervals");
        listDataHeader.add(interval);

        ExpandedMenuModel period = new ExpandedMenuModel();
        period.setIconName("Periods");
        listDataHeader.add(period);

        ExpandedMenuModel other = new ExpandedMenuModel();
        other.setIconName("Others");
        listDataHeader.add(other);

        // Adding child data
        List<String> topContacts = new ArrayList<>();
        topContacts.add("1");
        topContacts.add("3");
        topContacts.add("5");
        topContacts.add("10");
        topContacts.add("20");

        List<String> messageTypes = new ArrayList<>();
        messageTypes.add("Received");
        messageTypes.add("Sent");
        messageTypes.add("Both");

        List<String> intervals = new ArrayList<>();
        intervals.add("Minute");
        intervals.add("Hour");
        intervals.add("Day");
        intervals.add("Week");
        intervals.add("Month");
        intervals.add("Year");

        List<String> periods = new ArrayList<>();
        periods.add("Day");
        periods.add("Week");
        periods.add("Month");
        periods.add("Year");
        periods.add("All-Time");

        List<String> others = new ArrayList<>();
        others.add("Enable/Disable Legend");

        listDataChild.put(listDataHeader.get(0), topContacts);// Header, Child data
        listDataChild.put(listDataHeader.get(1), messageTypes);
        listDataChild.put(listDataHeader.get(2), intervals);
        listDataChild.put(listDataHeader.get(3), periods);
        listDataChild.put(listDataHeader.get(4), others);

        return new NavigationMenuHolder(listDataHeader, listDataChild);
    }

    public static NavigationMenuHolder prepareListDataForCardFragment() {
        List<ExpandedMenuModel> listDataHeader = new ArrayList<>();
        HashMap<ExpandedMenuModel, List<String>> listDataChild = new HashMap<>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("heading1");
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("heading2");
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("heading3");
        listDataHeader.add(item3);

        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Submenu of item 1");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Submenu of item 2");
        heading2.add("Submenu of item 2");
        heading2.add("Submenu of item 2");

        List<String> heading3 = new ArrayList<String>();
        heading3.add("Submenu of item 2");
        heading3.add("Submenu of item 2");
        heading3.add("Submenu of item 2");

        listDataChild.put(listDataHeader.get(0), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(1), heading2);
        listDataChild.put(listDataHeader.get(2), heading3);

        return new NavigationMenuHolder(listDataHeader, listDataChild);
    }
}
