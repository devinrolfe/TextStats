package com.nullnothing.relationshipstats.navigationMenu;

import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.CategoryUI;
import com.nullnothing.relationshipstats.enumsOrConstants.NavigationMenuUI;
import com.nullnothing.relationshipstats.enumsOrConstants.OtherUI;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeIntervalUI;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriodUI;
import com.nullnothing.relationshipstats.enumsOrConstants.TopContactsUI;

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
        topContact.setIconName(NavigationMenuUI.Top_NUM_CONTACTS.toString());
        listDataHeader.add(topContact);

        ExpandedMenuModel messageType = new ExpandedMenuModel();
        messageType.setIconName(NavigationMenuUI.MESSAGE_TYPES.toString());
        listDataHeader.add(messageType);

        ExpandedMenuModel interval = new ExpandedMenuModel();
        interval.setIconName(NavigationMenuUI.INTERVALS.toString());
        listDataHeader.add(interval);

        ExpandedMenuModel period = new ExpandedMenuModel();
        period.setIconName(NavigationMenuUI.PERIOD.toString());
        listDataHeader.add(period);

        ExpandedMenuModel other = new ExpandedMenuModel();
        other.setIconName(NavigationMenuUI.OTHER.toString());
        listDataHeader.add(other);

        // Adding child data
        List<String> topContacts = new ArrayList<>();
        topContacts.add(TopContactsUI.ONE.toString());
        topContacts.add(TopContactsUI.THREE.toString());
        topContacts.add(TopContactsUI.FIVE.toString());
        topContacts.add(TopContactsUI.TEN.toString());
        topContacts.add(TopContactsUI.TWENTY.toString());

        List<String> messageTypes = new ArrayList<>();
        messageTypes.add(CategoryUI.RECEIVED.toString());
        messageTypes.add(CategoryUI.SENT.toString());
        messageTypes.add(CategoryUI.BOTH.toString());

        List<String> intervals = new ArrayList<>();
        intervals.add(TimeIntervalUI.MINUTE.toString());
        intervals.add(TimeIntervalUI.HOUR.toString());
        intervals.add(TimeIntervalUI.DAY.toString());
        intervals.add(TimeIntervalUI.WEEK.toString());
        intervals.add(TimeIntervalUI.MONTH.toString());
        intervals.add(TimeIntervalUI.YEAR.toString());

        List<String> periods = new ArrayList<>();
        periods.add(TimePeriodUI.DAY.toString());
        periods.add(TimePeriodUI.WEEK.toString());
        periods.add(TimePeriodUI.MONTH.toString());
        periods.add(TimePeriodUI.YEAR.toString());
        periods.add(TimePeriodUI.ALL_TIME.toString());

        List<String> others = new ArrayList<>();
        others.add(OtherUI.LEGEND.toString());

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
