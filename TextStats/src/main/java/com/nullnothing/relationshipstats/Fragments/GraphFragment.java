package com.nullnothing.relationshipstats.Fragments;

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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nullnothing.relationshipstats.DataParserUtil;
import com.nullnothing.relationshipstats.DataPoint;
import com.nullnothing.relationshipstats.DataPointCollection;
import com.nullnothing.relationshipstats.DataStorageObjects.HashMapContactInfoHolder;
import com.nullnothing.relationshipstats.DataStorageObjects.MainInfoHolder;
import com.nullnothing.relationshipstats.DataStructures.ContactLinkedList;
import com.nullnothing.relationshipstats.DataStructures.ContactNode;
import com.nullnothing.relationshipstats.EnumsOrConstants.Category;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimeInterval;
import com.nullnothing.relationshipstats.EnumsOrConstants.TimePeriod;
import com.nullnothing.relationshipstats.R;
import com.nullnothing.relationshipstats.graphing.LineDataSetCreator;

import java.util.ArrayList;
import java.util.List;


public class GraphFragment extends Fragment implements FragmentInterface {

    private Context context;
    private LineChart mChart;
    private View view;

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
        /*
        TODO : Need to do stuff with messages, should display top 10 received contacts first
        I think is best option
         */

        MainInfoHolder mMainInfoHolder = MainInfoHolder.getInstance();
        List<String> contactList = mMainInfoHolder.getContactList();
        HashMapContactInfoHolder contacts = mMainInfoHolder.getContacts();

        // TODO : Everything that happens between time logging should go in its own method,
        // since this bit of code will be called a lot of times when changing settings
        long startTime = System.currentTimeMillis();
        Log.d("Graph Initial Setup", "START " + startTime);
        // TODO : BUG WITH END VALUE, Might be best to re write the add function, its a piece of crap
        ContactLinkedList contactLinkedList =
                DataParserUtil.getTopContactsInCategory(1, Category.RECEIVEDMSG, TimePeriod.ALL_TIME);

        List<String> xValueList = DataPointCollection.getXValues(TimeInterval.MONTH, TimePeriod.ALL_TIME, Category.RECEIVEDMSG);
        contactLinkedList.setXValues(xValueList);
        DataPointCollection.setYValues(contactLinkedList, xValueList, TimeInterval.MONTH, TimePeriod.ALL_TIME, Category.RECEIVEDMSG);

        Log.d("Graph Initial Setup", "END " + (System.currentTimeMillis() - startTime));

        List<LineDataSet> dataSets = getDataSets(contactLinkedList);

        setGraphData(xValueList, dataSets);

        // TODO : Delete this mockGraph bullshit
//         mockGraph();


    }

    public List<LineDataSet> getDataSets(ContactLinkedList contactLinkedList) {
        List<LineDataSet> dataSets = new ArrayList<>();
        ContactNode node = contactLinkedList.getHead();

        Category category = contactLinkedList.getCategory();
        List<String> xValues = contactLinkedList.getXValues();

        while(node != null) {
            List<LineDataSet> contactDataSets = getDataSet(node, xValues, category);
            for(LineDataSet mLineDataSet : contactDataSets) {
                dataSets.add(mLineDataSet);
            }
            node = node.next;
        }
        return dataSets;
    }

    public List<LineDataSet> getDataSet(ContactNode node, List<String> xValues, Category category) {
        return LineDataSetCreator.getInstance(context).getDataSet(node, xValues, category);
    }

    public void setGraphData(List<String> xVals, List<LineDataSet> mDataSets) {
        LineData data = new LineData(xVals, mDataSets);
        mChart.setData(data);
        mChart.notifyDataSetChanged(); // let the chart know it's data changed
        mChart.invalidate();
    }



    // TODO : Need to redraw graph when removing/adding contact to graph cuz only .setData(LineDataSet)
    // then notify that datasert has changed
    public void mockGraph() {

        ArrayList xVals = new ArrayList();

        for (int i = 0; i < 20; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < 20; i++) {

            float mult = (20 + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }


        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(10f, 5f, 0f);
//        set1.enableDashedHighlightLine(10f, 5f, 0f);

        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
//        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets



        // DONE making function for below bit

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);


        mChart.notifyDataSetChanged(); // let the chart know it's data changed
        mChart.invalidate();



    }



}


