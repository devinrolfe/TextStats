package com.nullnothing.relationshipstats.graphing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import com.github.mikephil.charting.data.LineDataSet;
import com.nullnothing.relationshipstats.DataStructures.ContactNode;
import com.nullnothing.relationshipstats.EnumsOrConstants.Category;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LineDataSetCreator {

    private static Context context;
    public static LineDataSetCreator instance = null;
    private static List<Integer> colours;


    public LineDataSetCreator(Context context){
        if(context != null) this.context = context;
        generateColours();
    }

    public static LineDataSetCreator getInstance(Context context) {

        if (instance == null) {
            instance = new LineDataSetCreator(context);
        }
        return instance;
    }

    private static void generateColours() {
        XMLColourPullParserHandler parser = new XMLColourPullParserHandler();
        try {
            colours = parser.parse(context.getAssets().open("graph_colours.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LineDataSet> getDataSet(ContactNode node, List<String> xValues, Category category) {
        List<LineDataSet> dataSetList = new ArrayList<>();
        LineDataSet mLineDataSet;

        if(category == Category.SENTANDRECEIVEDMSG) {
            mLineDataSet = new LineDataSet(node.getReceivedYValues(xValues), node.getData().getName());
            mLineDataSet = setDataSetInfo(mLineDataSet, false);
            dataSetList.add(mLineDataSet);

            int colour = mLineDataSet.getColor(0);

            mLineDataSet = new LineDataSet(node.getSentYValues(xValues), node.getData().getName());
            mLineDataSet = setDataSetInfo(mLineDataSet, true, colour);
            dataSetList.add(mLineDataSet);
        }
        else if(category == Category.RECEIVEDMSG) {
            mLineDataSet = new LineDataSet(node.getReceivedYValues(xValues), node.getData().getName());
            mLineDataSet = setDataSetInfo(mLineDataSet, false);
            dataSetList.add(mLineDataSet);
        }
        else if(category == Category.SENTMSG) {
            mLineDataSet = new LineDataSet(node.getSentYValues(xValues), node.getData().getName());
            mLineDataSet = setDataSetInfo(mLineDataSet, false);
            dataSetList.add(mLineDataSet);
        }
        return dataSetList;
    }

    public LineDataSet setDataSetInfo(LineDataSet mLineDataSet, boolean enableDash) {
        return setDataSetInfo(mLineDataSet, enableDash, -1);
    }

    public LineDataSet setDataSetInfo(LineDataSet mLineDataSet, boolean enableDash, int colour) {

        if(!enableDash) {
            colour = colours.remove(0);
            colours.add(colour); //Recycles colours
        }

        mLineDataSet.setColor(colour);
        mLineDataSet.setCircleColor(colour);
        mLineDataSet.setLineWidth(1f);
        mLineDataSet.setCircleSize(3f);
        mLineDataSet.setDrawCircleHole(false);
        mLineDataSet.setValueTextSize(9f);
        mLineDataSet.setFillAlpha(65);
        mLineDataSet.setFillColor(colour);

        mLineDataSet.setHighlightEnabled(true);
        mLineDataSet.setHighLightColor(Color.WHITE);

        if(enableDash) {
            mLineDataSet.enableDashedLine(10f, 5f, 0f);
            mLineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        }
        return mLineDataSet;
    }

}
