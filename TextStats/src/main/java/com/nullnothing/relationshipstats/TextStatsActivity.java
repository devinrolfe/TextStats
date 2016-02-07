package com.nullnothing.relationshipstats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import android.widget.TextView;

import com.nullnothing.relationshipstats.BackgroundProcessing.CollectDataBackground;
import com.nullnothing.relationshipstats.EnumsOrConstants.Constants;
import com.nullnothing.relationshipstats.Fragments.FragmentInterface;
import com.nullnothing.relationshipstats.Fragments.GraphListener;
import com.nullnothing.relationshipstats.Fragments.RawDataFragment;
import com.nullnothing.relationshipstats.Fragments.TextStatsFragmentPagerAdapter;

import java.util.ArrayList;

public class TextStatsActivity extends AppCompatActivity
        implements RawDataFragment.RawDataSelectedListener, GraphListener {

    public static boolean backgroundCollectionDone = false;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ViewPager viewPager;

    private CollectDataReceiver mCollectDataReceiver;
//    private Intent collectServiceIntent;
    private ArrayList<String> textMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationship_stats);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TextStatsFragmentPagerAdapter(getSupportFragmentManager(), TextStatsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /* TODO: Need to wait for it to finish collecting data before we display the graph, atm
            we can just have a static boolean value to do thing bit.
        */
        collectData();
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_add_contact:
               openAddContact();
                return true;
            case R.id.action_option:
                mDrawer.openDrawer(GravityCompat.END);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSearch() {

    }

    public void openAddContact() {

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
    public void changeGraphTitle(String title) {
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

            switch(intent.getIntExtra(Constants.EXTENDED_DATA_STATUS, -1)) {
                case Constants.STATE_ACTION_COMPLETE:
                    Log.d("CollectData", "Finished");

                    // TODO: Call method to current fragment to upload data
                    FragmentInterface fragment = (FragmentInterface) getSupportFragmentManager().findFragmentByTag(
                            "android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

                    fragment.initialSetup();

//                    textMessages = intent.getStringArrayListExtra(Constants.EXTENDED_DATA_TEXTLIST);
                    break;
                default:
                    break;
            }
        }
    }
}
