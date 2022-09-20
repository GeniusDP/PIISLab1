package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.IntPair;
import com.kpi.fics.piis.zaranik.utils.IntTriade;
import lombok.NoArgsConstructor;

import java.util.*;

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
        Queue<IntPair> q = new ArrayDeque<>();
        boolean[] used = new boolean[n * m];
        int[][] a = matrix.getArray();

        int startId = start.getPointId(matrix.getN());
        int finishId = finish.getPointId(matrix.getN());


        q.add(IntPair.of(startId, 1));
        while (!q.isEmpty()) {
            IntPair current = q.poll();
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

                int rightCeilId = currX * n + (currY + 1);
                int upCeilId = (currX + 1) * n + currY;
                int leftCeilId = currX * n + (currY - 1);
                int downCeilId = (currX - 1) * n + currY;

                int hToRight = h[rightCeilId][finishId];
                int hToUp = h[upCeilId][finishId];
                int hToLeft = h[leftCeilId][finishId];
                int hToDown = h[downCeilId][finishId];

                List<IntTriade> neighbourHeuristics = new ArrayList<>();
                neighbourHeuristics.add(IntTriade.of(hToRight, 0, 1));
                neighbourHeuristics.add(IntTriade.of(hToUp, 1, 0));
                neighbourHeuristics.add(IntTriade.of(hToLeft, 0, -1));
                neighbourHeuristics.add(IntTriade.of(hToDown, -1, 0));

                Collections.sort(neighbourHeuristics);

                for (var x : neighbourHeuristics) {
                    int di = x.second;
                    int dj = x.third;

                    if (di == 0 && dj == 1) {
                        relax(a[currX][currY + 1], rightCeilId, q, used, currVertexDist + 1);
                        continue;
                    }

                    if (di == 1 && dj == 0) {
                        relax(a[currX + 1][currY], upCeilId, q, used, currVertexDist + 1);
                        continue;
                    }

                    if (di == 0 && dj == -1) {
                        relax(a[currX][currY - 1], leftCeilId, q, used, currVertexDist + 1);
                        continue;
                    }

                    relax(a[currX - 1][currY], downCeilId, q, used, currVertexDist + 1);
                }

            }
        }
        if (a[finishId / n][finishId % n] == 0) {
            throw new NoWayFoundException("OOOPS! NO WAY!");
        }
        return new Matrix(a);
    }

    private void relax(int ceilValue, int ceilId, Queue<IntPair> q, boolean[] used, int dist) {
        if (ceilValue != -1 && !used[ceilId]) {
            q.add(IntPair.of(ceilId, dist));
        }
    }

}
