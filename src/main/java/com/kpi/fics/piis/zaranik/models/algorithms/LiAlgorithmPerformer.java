package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.IntPair;
import com.kpi.fics.piis.zaranik.utils.IntTriade;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class LiAlgorithmPerformer extends AbstractAlgorithm {

    public LiAlgorithmPerformer(HeuristicCalculator heuristicCalculator) {
        super(heuristicCalculator);
    }

    @Override
    public Matrix perform(Matrix matrix, Point start, Point finish) {
        int n = matrix.getN();
        int m = matrix.getM();

        int[][] h = heuristicFinder.findHeuristic(matrix.getN(), matrix.getM());
        Queue<IntTriade> q = new PriorityQueue<>(Comparator.comparingInt(t -> t.third));
        boolean[] used = new boolean[n * m];
        int[][] a = matrix.getArray();

        int startId = start.getPointId(matrix.getN());
        int finishId = finish.getPointId(matrix.getN());



        q.add(IntTriade.of(startId, 1, h[startId][finishId]));
        while (!q.isEmpty()) {
            IntTriade current = q.poll();
            int currVertexId = current.first;
            int currVertexDist = current.second;
            if (!used[currVertexId]) {
                used[currVertexId] = true;
                int currX = currVertexId / n;
                int currY = currVertexId % n;
                a[currX][currY] = currVertexDist;
                if (current.first == finish.getPointId(n)) {
                    q.clear();
                    break;
                }

                int rightCeilId = currX * n + currY + 1;
                int leftCeilId = currX * n + currY - 1;
                int upCeilId = (currX + 1) * n + currY;
                int downCeilId = (currX - 1) * n + currY;

                int heuristicFromRight = h[rightCeilId][finishId];
                int heuristicFromLeft = h[currX * n + currY - 1][finishId];
                int heuristicFromUp = h[(currX + 1) * n + currY][finishId];
                int heuristicFromDown = h[(currX - 1) * n + currY][finishId];

                relax(a[currX][currY + 1], rightCeilId, q, used, currVertexDist+1, heuristicFromRight);
                relax(a[currX][currY - 1], leftCeilId, q, used, currVertexDist+1, heuristicFromLeft);
                relax(a[currX + 1][currY], upCeilId, q, used, currVertexDist+1, heuristicFromUp);
                relax(a[currX - 1][currY], downCeilId, q, used, currVertexDist+1, heuristicFromDown);
            }
        }

        return new Matrix(a);
    }

    private void relax(int ceilValue, int ceilId, Queue<IntTriade> q, boolean[] used, int dist, int heuristicEstimate) {
        if (ceilValue != -1 && !used[ceilId]) {
            q.add(IntTriade.of(ceilId, dist, heuristicEstimate));
        }
    }

}
