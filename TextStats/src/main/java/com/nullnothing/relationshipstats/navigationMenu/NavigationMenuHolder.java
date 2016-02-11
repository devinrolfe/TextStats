package com.nullnothing.relationshipstats.navigationMenu;

import java.util.HashMap;
import java.util.List;

public class NavigationMenuHolder {

    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;

    public NavigationMenuHolder(List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<String>> listDataChild) {
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    public List<ExpandedMenuModel> getListDataHeader() { return listDataHeader; }
    public HashMap<ExpandedMenuModel, List<String>> getListDataChild() { return listDataChild; }
}
