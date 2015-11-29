package com.nullnothing.relationshipstats;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;


public class GraphFragment extends Fragment implements FragmentInterface {

    private LineChart mChart;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_graph, container, false);

        setupGraph();
        return view;
    }

    private void setupGraph() {
        mChart = new LineChart(this.getActivity());

        mChart.setDescription("");
        mChart.setNoDataTextDescription("Loading text messages...");

        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        mChart.setPinchZoom(true);

        mChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        l.setForm(LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextColor(Color.BLACK);

        XAxis xl = mChart.getXAxis();
        xl.setTypeface(tf);
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setSpaceBetweenLabels(5);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setStartAtZero(true);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        FrameLayout frameLayout = (FrameLayout) view;
        frameLayout.addView(mChart);
    }

    public void initialSetup() {

        MainInfoHolder mMainInfoHolder = MainInfoHolder.getInstance();


        ContactInfoHolder lukeContact = mMainInfoHolder.getContacts().get("49").getValue();

        ArrayList receivedMessages = lukeContact.getReceivedMessages();
        ArrayList sentMessages = lukeContact.getSentMessages();

        /*
        TODO : Need to do stuff with messages, should display top 10 received contacts first
        I think is best option
         */

        /*
        TODO : Use CalandarHelper class to help with which messages we should be capturing
         */


//        ReceivedMessage mReceived = (ReceivedMessage)receivedMessages.get(0);
//        SentMessage mSent = (SentMessage)sentMessages.get(0);


//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(mReceived.getTimestamp());
//        cal.get(Calendar.YEAR);






    }





}


