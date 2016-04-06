package com.nullnothing.relationshipstats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.nullnothing.relationshipstats.ActivityHelpers.ActionBarButtonUpdater;
import com.nullnothing.relationshipstats.ActivityHelpers.ContactsAvailability;
import com.nullnothing.relationshipstats.backgroundProcessing.CollectDataBackground;
import com.nullnothing.relationshipstats.builders.GraphBuilderHelper;
import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.Constants;
import com.nullnothing.relationshipstats.enumsOrConstants.FragmentName;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.fragments.CardFragment;
import com.nullnothing.relationshipstats.fragments.FragmentInterface;
import com.nullnothing.relationshipstats.fragments.GraphFragment;
import com.nullnothing.relationshipstats.fragments.MenuListener;
import com.nullnothing.relationshipstats.fragments.RawDataFragment;
import com.nullnothing.relationshipstats.fragments.TextStatsFragmentPagerAdapter;
import com.nullnothing.relationshipstats.fragments.TitleListener;
import com.nullnothing.relationshipstats.navigationMenu.ExpandableListAdapter;
import com.nullnothing.relationshipstats.navigationMenu.ExpandedMenuModel;
import com.nullnothing.relationshipstats.navigationMenu.NavigationMenuChanger;
import com.nullnothing.relationshipstats.navigationMenu.NavigationMenuHolder;
import com.nullnothing.relationshipstats.requests.GraphChangeRequest;
import com.nullnothing.relationshipstats.util.NavigationMenuChangeHelper;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextStatsActivity extends AppCompatActivity
        implements RawDataFragment.RawDataSelectedListener, TitleListener, MenuListener {

    public static boolean backgroundCollectionDone = false;

    private DrawerLayout mDrawerLayout;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;

    private Toolbar toolbar;
    public ViewPager viewPager;

    private CollectDataReceiver mCollectDataReceiver;
//    private Intent collectServiceIntent;
    private ArrayList<String> textMessages;

    private Menu activityMenu;
    private ContactsAvailability contactsAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationship_stats);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TextStatsFragmentPagerAdapter(getSupportFragmentManager(), TextStatsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        collectData();
    }

    public void changeNavigationMenu(FragmentName fragmentName) {
        NavigationMenuHolder navigationMenuHolder;

        switch (fragmentName) {
            case GraphFragment:
                navigationMenuHolder = NavigationMenuChanger.prepareListDataForGraphFragment();
                break;
            case CardFragment:
//                navigationMenuHolder = NavigationMenuChanger.prepareListDataForCardFragment();
                navigationMenuHolder = NavigationMenuChanger.prepareListDataForGraphFragment();
                break;
            default:
                throw new IllegalArgumentException();
        }

        listDataHeader = navigationMenuHolder.getListDataHeader();
        listDataChild = navigationMenuHolder.getListDataChild();
        mMenuAdapter = new ExpandableListAdapter(this, navigationMenuHolder.getListDataHeader(), navigationMenuHolder.getListDataChild(), expandableList);
        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                Log.d("onNavigationItem", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString());

                FragmentInterface fragment = (FragmentInterface) getSupportFragmentManager().findFragmentByTag(
                        "android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

                NavigationMenuChangeHelper.NavigationMenuChangeHelper(fragment,
                        listDataChild,
                        listDataHeader,
                        groupPosition,
                        childPosition);

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void collectData() {
        // Create receiver
        mCollectDataReceiver = new CollectDataReceiver();
        // Intent to receive status updates
        IntentFilter mStatusIntentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        mStatusIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        // match the receiver with the status intent
        LocalBroadcastManager.getInstance(this).registerReceiver(mCollectDataReceiver, mStatusIntentFilter);

        // Create background intent & start the thread
        Intent collectServiceIntent = new Intent(this, CollectDataBackground.class);
        collectServiceIntent.setData(Uri.parse("InitialCollectionSetup"));
        this.startService(collectServiceIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relationship_stats, menu);
        activityMenu = menu;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_contact:
               openAddContact();
                return true;
            case R.id.action_delete_contact:
                openDeleteContact();
                return true;
            case R.id.action_clear_contact:
                openClearContact();
                return true;
            case R.id.action_option:
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Functions below are not used, just place holders for now
    public void openAddContact() {
    }

    public void openDeleteContact() {
    }

    public void openClearContact() {

        ArrayList<GraphChangeRequest> prevRequests = GraphChangeRequest.prevRequests;

        GraphChangeRequestBuilder builder = new GraphChangeRequestBuilder();
        if (prevRequests.size() > 0) {
            ContactLinkedList contactLinkedList = new ContactLinkedList(0,
                    prevRequests.get(prevRequests.size() - 1).getCategory(),
                    prevRequests.get(prevRequests.size() - 1).getPeriod());

            builder.contactsToGraph(contactLinkedList);

            GraphBuilderHelper.setGraphNotChangedValues(builder);
            builder.build().executeRequest();
        }
    }

    // Get next text message in sms logs
    public void getNextText(View view) {
        RawDataFragment rawDataFrag = (RawDataFragment) getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

        if (rawDataFrag != null) {
            String sms = null;
            if(textMessages != null && textMessages.size() > 1) {
                sms = textMessages.remove(0);
            }

            rawDataFrag.getNextTextFromActivity(sms);
        }
    }

    // Set the Title of the Activity to title
    public void changeTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.graphTitleId);
        textView.setText(title);
    }

    // Broadcast receiver for receiving status updates from the IntentService
    private class CollectDataReceiver extends BroadcastReceiver {

        // Prevents instantiation
        private CollectDataReceiver() {
        }
        // Called when intent is received which it registered
        public void onReceive(Context context, Intent intent) {
            FragmentInterface fragment;
            switch(intent.getIntExtra(Constants.EXTENDED_DATA_STATUS, -1)) {
                case Constants.STATE_ACTION_COMPLETE:
                    Log.d("CollectData", "Finished");

                    List<Fragment> fragments = getSupportFragmentManager().getFragments();

                    for (Fragment fragmentInList : fragments) {
                        if (fragmentInList instanceof  GraphFragment) {
                            ((GraphFragment) fragmentInList).initialSetup();
                        }
                    }
                    break;
                case Constants.CHANGE_GRAPH_REQUEST :
                    handleChangeGraphRequest(intent);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleChangeGraphRequest(Intent intent) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for (Fragment fragmentInList : fragments) {

            if (fragmentInList instanceof  GraphFragment) {
                changeGraphFragment((FragmentInterface) fragmentInList, intent);
            } else if (fragmentInList instanceof CardFragment) {
                // TODO : Need to create raw list
                ((CardFragment) fragmentInList).initialSetup();

                changeCardFragment((FragmentInterface) fragmentInList, intent);
            }

        }
    }

    private void changeGraphFragment(FragmentInterface fragment, Intent intent) {
        ContactLinkedList contactLinkedList = (ContactLinkedList)intent.getParcelableExtra(Constants.EXTENDED_DATA_CONTACTS);
        contactsAvailability = new ContactsAvailability(contactLinkedList);

        ActionBarButtonUpdater.updateActionBarButtons(activityMenu, contactsAvailability);

        ((GraphFragment) fragment).changeGraph(
                contactLinkedList,
                Category.getValueOf(intent.getStringExtra(Constants.EXTENDED_DATA_CATEGORY)),
                TimeInterval.getValueOf(intent.getStringExtra(Constants.EXTENDED_DATA_INTERVAL)),
                TimePeriod.getValueOf(intent.getStringExtra(Constants.EXTENDED_DATA_PERIOD)),
                intent.getBooleanExtra(Constants.EXTENDED_DATA_DISABLE_LEGEND, false)
        );
    }

    private void changeCardFragment(FragmentInterface fragment, Intent intent) {
        ContactLinkedList contactLinkedList = (ContactLinkedList)intent.getParcelableExtra(Constants.EXTENDED_DATA_CONTACTS);
        contactsAvailability = new ContactsAvailability(contactLinkedList);

        ActionBarButtonUpdater.updateActionBarButtons(activityMenu, contactsAvailability);

        ((CardFragment) fragment).changeCard(
                contactLinkedList,
                Category.getValueOf(intent.getStringExtra(Constants.EXTENDED_DATA_CATEGORY)),
                TimeInterval.getValueOf(intent.getStringExtra(Constants.EXTENDED_DATA_INTERVAL)),
                TimePeriod.getValueOf(intent.getStringExtra(Constants.EXTENDED_DATA_PERIOD)),
                intent.getBooleanExtra(Constants.EXTENDED_DATA_DISABLE_LEGEND, false)
        );
    }


}
