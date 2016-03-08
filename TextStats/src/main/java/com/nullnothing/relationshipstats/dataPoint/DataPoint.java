package com.nullnothing.relationshipstats.dataPoint;

public class DataPoint {

    private int xValue;
    private int yValue;
    private String name;

    public DataPoint(int xValue) {
        this.xValue = xValue;
        this.yValue = 0;
    }

    public int getX() { return xValue; }
    public int getY() { return yValue; }
    public String getName() { return name; }
}
