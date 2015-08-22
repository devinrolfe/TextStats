package com.nullnothing.relationshipstats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RelationshipStatsActivity extends AppCompatActivity
        implements RawDataFragment.RawDataSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private Cursor cur;

    private CollectDataReceiver mCollectDataReceiver;
//    private Intent collectServiceIntent;


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
        viewPager.setAdapter(new RelationshipStatsFragmentPagerAdapter(getSupportFragmentManager(), RelationshipStatsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        collectData();
    }

    private void collectData() {
        // Create background intent & start the thread
        Intent collectServiceIntent = new Intent(this, CollectDataBackground.class);
        collectServiceIntent.setData(Uri.parse("InitialCollectionSetup"));
        this.startService(collectServiceIntent);

//        Intent getNextTextServiceIntent = new Intent(this, CollectDataBackground.class);
//        getNextTextServiceIntent.setData(Uri.parse("getNextText"));
//        this.startService(getNextTextServiceIntent);

        // Create receiver
        mCollectDataReceiver = new CollectDataReceiver();
        // Intent to receive status updates
        IntentFilter mStatusIntentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        // match the receiver with the status intent
        LocalBroadcastManager.getInstance(this).registerReceiver(mCollectDataReceiver, mStatusIntentFilter);
        // Intent to receive text messages
        IntentFilter mTextMessageIntentFilter = new IntentFilter(Constants.ACTION_TEXT_MESSAGE);
        // match the receiver with the text message intent
        LocalBroadcastManager.getInstance(this).registerReceiver(mCollectDataReceiver, mTextMessageIntentFilter);

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
        Intent getNextTextServiceIntent = new Intent(this, CollectDataBackground.class);
        getNextTextServiceIntent.setData(Uri.parse("getNextText"));
        this.startService(getNextTextServiceIntent);
    }

    // Broadcast receiver for receiving status updates from the IntentService
    private class CollectDataReceiver extends BroadcastReceiver {

        // Prevents instantiation
        private CollectDataReceiver() {
        }
        // Called when intent is received which it registered
        public void onReceive(Context context, Intent intent) {
            /**
             * Handle the intent here.
             */
            String action = intent.getAction();

            switch(action) {

                case Constants.BROADCAST_ACTION:

                    new AlertDialog.Builder(context)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    break;
                case Constants.ACTION_TEXT_MESSAGE:

                    String sms = intent.getStringExtra(Constants.EXTENDED_DATA_TEXT);

                    RawDataFragment rawDataFrag = (RawDataFragment) getSupportFragmentManager().findFragmentByTag(
                            "android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

                    if (rawDataFrag != null) {
                        rawDataFrag.getNextTextFromActivity(sms);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
