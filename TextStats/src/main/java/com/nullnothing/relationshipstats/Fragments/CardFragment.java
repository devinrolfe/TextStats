package com.nullnothing.relationshipstats.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nullnothing.relationshipstats.DataStorageObjects.MainInfoHolder;
import com.nullnothing.relationshipstats.R;


// In this case, the fragment displays simple text based on the page
public class CardFragment extends Fragment implements FragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        FrameLayout frameLayout = (FrameLayout) view;

        TextView textView = (TextView) frameLayout.getChildAt(0);
        textView.setText("Fragment: Card");
        return view;
    }




    public void initialSetup() {

        MainInfoHolder mMainInfoHolder = MainInfoHolder.getInstance();
    }


}


