package com.nullnothing.relationshipstats;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.TextView;

public class RelationshipStatsActivity extends AppCompatActivity
        implements RawDataFragment.RawDataSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private Cursor cur;

    private CollectDataReceiver mCollectDataReceiver;
    private Intent collectServiceIntent;


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

        // SOMETHING IS NOT WORKING PROPERLY
        /**
         * Intent filter will receive the status intent that the collectServiceIntent
         * will send after it has finished collecting all the data
         */
        // The filter's action is BROADCAST_ACTION
        IntentFilter statusIntentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        // Sets the filter's category to DEFAULT
        statusIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        // Instantiates a new DownloadStateReceiver
        mCollectDataReceiver = new CollectDataReceiver();
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(mCollectDataReceiver, statusIntentFilter);



        // Create background intent & start the thread
        collectServiceIntent = new Intent(this, CollectDataBackground.class);
        collectServiceIntent.setData(Uri.parse("InitialCollection"));
        this.startService(collectServiceIntent);




        //BELOW WILL GO TO BACKGROUND THREAD//////////////
        // use "content://sms/sent" to get text messages you sent
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        cur = getContentResolver().query(uriSMSURI, null, null, null, null);
        //////////////////////////////////////////////


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
        TextView textView = (TextView) findViewById(R.id.fragment_raw_data_text_view);

        String sms = "";
        if (cur.moveToNext()) {
            sms += "From :" + cur.getString(2) + " : " + cur.getString(cur.getColumnIndex("body"))+"\n";
            //return text message to fragment
        }

        RawDataFragment rawDataFrag = (RawDataFragment) getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

        if (rawDataFrag != null) {
            rawDataFrag.getNextTextFromActivity(sms);
        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(RelationshipStatsActivity.this);
            builder1.setMessage("Error: Something went wrong");
            builder1.setCancelable(true);

            AlertDialog alert11 = builder1.create();
            alert11.show();

            finish();
        }

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
        }

    }


}
