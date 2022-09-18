package com.kpi.fics.piis.zaranik.models.algorithms;

@FunctionalInterface
public interface HeuristicCalculator {
    int compute(int x1, int y1, int x2, int y2);
}
