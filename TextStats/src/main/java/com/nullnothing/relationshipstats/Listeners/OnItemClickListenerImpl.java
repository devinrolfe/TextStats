package com.nullnothing.relationshipstats.Listeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;

import com.nullnothing.relationshipstats.builders.GraphBuilderHelper;
import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.dataStorageObjects.ContactInfoHolder;
import com.nullnothing.relationshipstats.dataStorageObjects.MainInfoHolder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.enumsOrConstants.Constants;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;
import com.nullnothing.relationshipstats.util.NavigationMenuChangeHelper;

import java.util.ArrayList;

public class OnItemClickListenerImpl implements OnMenuItemClickListener {

    private String action;

    public OnItemClickListenerImpl(String action) {
        this.action = action;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        GraphChangeRequestBuilder builder = new GraphChangeRequestBuilder();
        String rawId = String.valueOf(item.getItemId());

        ArrayList<GraphChangeRequest> prevRequests = GraphChangeRequest.prevRequests;
        ContactLinkedList contactLinkedList;
        //action is either add or delete
        if (prevRequests.size() > 0) {
            contactLinkedList = prevRequests.get(prevRequests.size() - 1).getContactsToGraph();

            if (action.equals(Constants.ADD_CONTACT_ACTION)) {
                addContactToGraph(contactLinkedList, rawId);
            }
            else if (action.equals(Constants.DELETE_CONTACT_ACTION)) {
                deleteContactToGraph(contactLinkedList, rawId);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

        builder.contactsToGraph(contactLinkedList);

        GraphBuilderHelper.setGraphNotChangedValues(builder);
        builder.build().executeRequest();

        return true;
    }

    private void addContactToGraph(ContactLinkedList contactLinkedList, String rawId) {
        ContactInfoHolder contactInfoHolder = MainInfoHolder.getInstance().getContacts().get(rawId).getValue();
        contactLinkedList.add(contactInfoHolder, true);

    }

    private void deleteContactToGraph(ContactLinkedList contactLinkedList, String rawId) {
        ContactInfoHolder contactInfoHolder = MainInfoHolder.getInstance().getContacts().get(rawId).getValue();
        contactLinkedList.remove(contactInfoHolder);
    }

}
