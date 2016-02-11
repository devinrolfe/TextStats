package com.nullnothing.relationshipstats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nullnothing.relationshipstats.enumsOrConstants.FragmentName;
import com.nullnothing.relationshipstats.R;


// In this case, the fragment displays simple text based on the page
public class RawDataFragment extends Fragment {
    RawDataSelectedListener mCallback;

    private TextView textMessageView;

    // Container Activity must implement this interface
    public interface RawDataSelectedListener {
        public void getNextText(View view);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (RawDataSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RawDataSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raw_data, container, false);
//        FrameLayout frameLayout = (FrameLayout) view;
        textMessageView = (TextView) view.findViewById(R.id.fragment_raw_data_text_view);
        textMessageView.setText(this.getTag());
        return view;
    }

    public FragmentName getName() { return FragmentName.RawDataFragment; }

    // Get the next text message and display it
    public void getNextTextFromActivity(String message) {
        if(message != null) {
            textMessageView.setText(message);
        }else {
            textMessageView.setText("FINISHED");
        }
    }

}


