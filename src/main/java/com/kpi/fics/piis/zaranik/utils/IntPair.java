package com.kpi.fics.piis.zaranik.utils;

public class IntPair implements Comparable{
    public int first;
    public int second;

    public IntPair(int first, int second){
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(Object o) {
        IntPair another = (IntPair) o;
        return this.first - another.first;
    }
}
