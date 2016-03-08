package com.nullnothing.relationshipstats.ActivityHelpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.nullnothing.relationshipstats.Listeners.OnItemClickListenerImpl;
import com.nullnothing.relationshipstats.R;
import com.nullnothing.relationshipstats.enumsOrConstants.Constants;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class ActionBarButtonUpdater {

    private ActionBarButtonUpdater() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void updateActionBarButtons(Menu activityMenu, ContactsAvailability contactsAvailability) {
        updateMenu(activityMenu.getItem(0), contactsAvailability.getNotDisplayedContacts(), Constants.ADD_CONTACT_ACTION);
        updateMenu(activityMenu.getItem(1), contactsAvailability.getDisplayedContacts(), Constants.DELETE_CONTACT_ACTION);
    }

    private static void updateMenu(MenuItem menuItem, Map<String, String> map, String action) {

        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.clear();

        ContactItem[] contactNames = new ContactItem[map.size()];
        int pos = 0;

        for(Map.Entry<String, String> entry : map.entrySet()) {
            contactNames[pos++] = new ContactItem(entry.getKey(), entry.getValue());
        }
        Arrays.sort(contactNames);

        MenuItem contactItem;

        for (int i=0; i < contactNames.length; i++) {
            contactItem = subMenu.add(0, Integer.parseInt(contactNames[i].getId()), 0, contactNames[i].getName());
            contactItem.setOnMenuItemClickListener(new OnItemClickListenerImpl(action));
        }
    }

    private static class ContactItem implements Comparable {

        private String rawId;
        private String name;

        public ContactItem(String rawId, String name) {
            this.rawId = rawId;
            this.name = name;
        }

        public String getId() {
            return rawId;
        }

        public String getName() {
            return name;
        }

        @Override
        public int compareTo(Object another) {

            if (another instanceof ContactItem) {
                ContactItem other = (ContactItem) another;
                if (other.getName() != null) {
                    return this.getName().compareTo(((ContactItem) another).getName());
                }
                else {
                    return -1;
                }
            }
            return -1;
        }
    }

}
