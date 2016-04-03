package com.nullnothing.relationshipstats.requests;

import android.support.v4.app.FragmentActivity;

import com.nullnothing.relationshipstats.builders.Builder;

public interface Request {
    void executeRequest();
    boolean isPreviousRequest();
}
