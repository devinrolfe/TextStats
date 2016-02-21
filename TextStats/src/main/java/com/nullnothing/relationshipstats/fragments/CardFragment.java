package com.nullnothing.relationshipstats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nullnothing.relationshipstats.dataStorageObjects.MainInfoHolder;
import com.nullnothing.relationshipstats.enumsOrConstants.FragmentName;
import com.nullnothing.relationshipstats.R;


// In this case, the fragment displays simple text based on the page
public class CardFragment extends Fragment implements FragmentInterface {

    MenuListener mMenuListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        FrameLayout frameLayout = (FrameLayout) view;

        TextView textView = (TextView) frameLayout.getChildAt(0);
        textView.setText("Fragment: Card");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // TODO should implement TitleListener to change title
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
            mMenuListener.changeNavigationMenu(FragmentName.CardFragment);
        }
    }

    public FragmentName getName() { return FragmentName.CardFragment; }


    public void initialSetup() {

        MainInfoHolder mMainInfoHolder = MainInfoHolder.getInstance();
    }


}


