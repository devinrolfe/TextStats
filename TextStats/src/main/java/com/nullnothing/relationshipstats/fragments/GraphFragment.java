package com.nullnothing.relationshipstats.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nullnothing.relationshipstats.dataPoint.DataParserUtil;
import com.nullnothing.relationshipstats.dataPoint.DataPointCollection;
import com.nullnothing.relationshipstats.R;
import com.nullnothing.relationshipstats.builders.GraphChangeRequestBuilder;
import com.nullnothing.relationshipstats.dataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.dataStructures.ContactNode;
import com.nullnothing.relationshipstats.enumsOrConstants.Category;
import com.nullnothing.relationshipstats.enumsOrConstants.FragmentName;
import com.nullnothing.relationshipstats.enumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.enumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.graphing.CustomMarkerView;
import com.nullnothing.relationshipstats.graphing.LineDataSetCreator;

import java.util.ArrayList;
import java.util.List;


public class GraphFragment extends Fragment implements FragmentInterface {

    private Context context;
    private LineChart mChart;
    private View view;

    TitleListener mTitleListener;
    MenuListener mMenuListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mTitleListener = (TitleListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TitleListener");
        }
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
            mMenuListener.changeNavigationMenu(FragmentName.GraphFragment);
        }
    }

    public FragmentName getName() { return FragmentName.GraphFragment; }


    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_graph, container, false);
        setupGraph(getActivity());
        return view;
    }

    private void setupGraph(Context context) {
        setContext(context);

        mChart = new LineChart(this.getActivity());

        mChart.setNoDataText("Loading text messages...");
        mChart.getPaint(Chart.PAINT_INFO).setColor(Color.BLACK);

        // Custom highlight marker
        CustomMarkerView mv = new CustomMarkerView(context, R.layout.graph_marker);
        // set the marker to the chart
        mChart.setMarkerView(mv);

        mChart.setDescription("");

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

        l.setWordWrapEnabled(true);
        l.setForm(LegendForm.CIRCLE);
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

        ContactLinkedList contactsToGraph = DataParserUtil.getTopContactsInCategory(3, Category.RECEIVEDMSG, TimePeriod.ALL_TIME);

        new GraphChangeRequestBuilder()
                .contactsToGraph(contactsToGraph)
                .category(Category.RECEIVEDMSG)
                .interval(TimeInterval.MONTH)
                .period(TimePeriod.ALL_TIME)
                .build()
                .executeRequest();
    }

    private List<LineDataSet> getDataSets(ContactLinkedList contactLinkedList) {
        List<LineDataSet> dataSets = new ArrayList<>();
        ContactNode node = contactLinkedList.getHead();

        Category category = contactLinkedList.getCategory();
        List<String> xValues = contactLinkedList.getXValues();

        int index = 0;
        int[] colours = new int[contactLinkedList.getSize()];
        String[] labels = new String[contactLinkedList.getSize()];

        while(node != null) {
            List<LineDataSet> contactDataSets = getDataSet(node, xValues, category);

            colours[index] = contactDataSets.get(0).getColor(0);
            labels[index] = contactDataSets.get(0).getLabel();

            for(LineDataSet mLineDataSet : contactDataSets) {
                dataSets.add(mLineDataSet);
            }
            node = node.next;
            index++;
        }
        mChart.getLegend().setCustom(colours, labels);

        return dataSets;
    }

    private List<LineDataSet> getDataSet(ContactNode node, List<String> xValues, Category category) {
        return LineDataSetCreator.getInstance(context).getDataSet(node, xValues, category);
    }

    private void setGraphData(List<String> xVals, List<LineDataSet> mDataSets) {
        LineData data = new LineData(xVals, mDataSets);
        mChart.setData(data);
        mChart.notifyDataSetChanged(); // let the chart know it's data changed
        mChart.invalidate();
    }

    public void changeGraph(ContactLinkedList contactLinkedList, Category category, TimeInterval interval, TimePeriod period, boolean disableLegend) {

        mChart.getLegend().setEnabled(!disableLegend);
        mChart.setNoDataText("Loading text messages...");

        LineDataSetCreator.resetColours();

        long startTime = System.currentTimeMillis();
        Log.d("Graph Initial Setup", "START " + startTime);

        List<String> xValueList = DataPointCollection.getXValues(interval, period, category);
        contactLinkedList.setXValues(xValueList);
        DataPointCollection.setYValues(contactLinkedList, xValueList, interval, period, category);

        List<LineDataSet> dataSets = getDataSets(contactLinkedList);

        setGraphData(xValueList, dataSets);

        switch (category) {
            case SENTANDRECEIVEDMSG:
                mTitleListener.changeTitle("Text Messages : Received(solid), Sent(dotted)");
                break;
            case SENTMSG:
                mTitleListener.changeTitle("Text Messages : Sent");
                break;
            case RECEIVEDMSG:
                mTitleListener.changeTitle("Text Messages : Received");
                break;
            default:
                break;
        }
        mChart.setNoDataText("No Data To show");
        Log.d("Graph Initial Setup", "END " + (System.currentTimeMillis() - startTime));
    }

}


