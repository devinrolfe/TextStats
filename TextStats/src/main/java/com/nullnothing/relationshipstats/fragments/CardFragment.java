package com.nullnothing.relationshipstats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.data.LineDataSet;
import com.nullnothing.relationshipstats.dataPoint.DataPointCollection;
import com.nullnothing.relationshipstats.dataStorageObjects.ContactInfoHolder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.dataStructures.ContactNode;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.FragmentName;
import com.nullnothing.relationshipstats.R;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.graphing.LineDataSetCreator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


// In this case, the fragment displays simple text based on the page
public class CardFragment extends ListFragment implements FragmentInterface {

    MenuListener mMenuListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // TODO should implement TitleListener to change title
        // not implemented
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                mMenuListener = (MenuListener) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString()
                        + " must implement MenuListener");
            }
            mMenuListener.changeNavigationMenu(FragmentName.CardFragment);
        }
    }

    public FragmentName getName() { return FragmentName.CardFragment; }


    public void initialSetup() {
        // nothing to do
    }


    public void changeCard(ContactLinkedList contactLinkedList, Category category, TimeInterval interval, TimePeriod period, boolean disableLegend) {
        long startTime = System.currentTimeMillis();
        Log.d("Card change", "START " + startTime);

        List<String> contactNames = new ArrayList<String>();

        List<ContactInfoHolder> contactInfoHolder = new ArrayList<ContactInfoHolder>();
        for(Map.Entry<String, ContactNode> entry : contactLinkedList.getMap().entrySet()) {
            if (entry.getValue() != null){
                contactInfoHolder.add(entry.getValue().getData());
            }
        }
        Collections.sort(contactInfoHolder, new ContactComparator());

        for(ContactInfoHolder contact : contactInfoHolder) {
            contactNames.add(constructContactTextViewString(contact, category, interval, period));
        }


        ListView listView = (ListView) getView().findViewById(R.id.fragment_card);
        String[] statesList = contactNames.toArray(new String[contactNames.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, statesList);
        listView.setAdapter(adapter);
        Log.d("Card change", "END " + (System.currentTimeMillis() - startTime));
    }

    static class ContactComparator implements Comparator {

        @Override
        public int compare(Object x, Object y) {

            if (!(x instanceof ContactInfoHolder)) {
                return -1;
            }
            if (!(y instanceof ContactInfoHolder)) {
                return 1;
            }

            ContactInfoHolder contact1 = (ContactInfoHolder) x;
            ContactInfoHolder contact2 = (ContactInfoHolder) y;

            return contact1.getName().compareTo(contact2.getName());
        }
    }


    private String constructContactTextViewString(ContactInfoHolder contact, Category category, TimeInterval interval, TimePeriod period) {

        StringBuffer contactString = new StringBuffer();

        contactString.append(contact.getName());
        contactString.append("\n\t");

        contactString.append("# : " + contact.getPrimaryPhoneNumber());
        contactString.append("\n\t");

        contactString.append("Total Texts : " + contact.getTextBothCount(period));
        contactString.append("\n\t");

        contactString.append("Sent Texts : " + contact.getTextSentCount(period));
        contactString.append("\n\t");

        contactString.append("Received Texts : " + contact.getTextReceivedCount(period));
        contactString.append("\n\t");

        int sent = contact.getTextSentCount(period);
        int received = contact.getTextReceivedCount(period);

        BigDecimal ratio = received != 0 ? new BigDecimal((double)sent/received) : new BigDecimal(sent);

        contactString.append("Sent/Received Texts Ratio : " + ratio.setScale(2, RoundingMode.CEILING));
        contactString.append("\n\t");

        return contactString.toString();
    }
}


