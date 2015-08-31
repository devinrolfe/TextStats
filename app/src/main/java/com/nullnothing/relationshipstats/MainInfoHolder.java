package com.nullnothing.relationshipstats;


import java.util.HashMap;

public class MainInfoHolder {

    private HashMap<String, ContactInfoHolder> map = new HashMap<String, ContactInfoHolder>();


    private static final MainInfoHolder holder = new MainInfoHolder();
    public static MainInfoHolder getInstance() { return holder; }


}
