package com.nullnothing.relationshipstats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


// In this case, the fragment displays simple text based on the page
public class RawDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raw_data, container, false);

        FrameLayout frameLayout = (FrameLayout) view;

        TextView textView = (TextView) frameLayout.getChildAt(0);
        textView.setText("Fragment: Raw Data");
        return view;
    }
}

