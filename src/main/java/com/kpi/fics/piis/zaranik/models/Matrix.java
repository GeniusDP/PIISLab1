package com.kpi.fics.piis.zaranik.models;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public class Matrix {
    public static final int MAX_SIZE = 200;

    private final int[][] array;
    private int n;
    private int m;
    @Setter
    private boolean performed = false;

    public Matrix(int[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("array should not be null!");
        }
        this.array = array;
        this.n = array.length;
        this.m = array[0].length;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] ints : array) {
            sb.append(Arrays.toString(ints));
            sb.append("\n");
        }
        return sb.toString();
    }
}
