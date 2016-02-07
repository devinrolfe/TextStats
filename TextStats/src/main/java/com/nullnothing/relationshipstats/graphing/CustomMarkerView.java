package com.nullnothing.relationshipstats.graphing;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.nullnothing.relationshipstats.R;

public class CustomMarkerView extends MarkerView {

    private TextView graphEntryLabel;

    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        graphEntryLabel = (TextView) findViewById(R.id.graphEntryLabel);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        EntryDataObject entryDataObject = (EntryDataObject) e.getData();
        graphEntryLabel.setText("" + entryDataObject.getName() + " : " + (int)e.getVal());
    }

    @Override
    public int getXOffset() {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset() {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}
