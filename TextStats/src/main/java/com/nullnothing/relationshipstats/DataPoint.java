package com.nullnothing.relationshipstats;

public class DataPoint {

    private int xValue;
    private int yValue;

    public DataPoint(int xValue) {
        this.xValue = xValue;
        this.yValue = 0;
    }

    public int getX() { return xValue; }
    public int getY() { return yValue; }

}
