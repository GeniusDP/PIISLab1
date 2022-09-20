package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.IntPair;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.Queue;

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

                int rightCeilId = currX * n + currY + 1;
                int leftCeilId = currX * n + currY - 1;
                int upCeilId = (currX + 1) * n + currY;
                int downCeilId = (currX - 1) * n + currY;

                relax(a[currX][currY + 1], rightCeilId, q, used, currVertexDist + 1);
                relax(a[currX][currY - 1], leftCeilId, q, used, currVertexDist + 1);
                relax(a[currX + 1][currY], upCeilId, q, used, currVertexDist + 1);
                relax(a[currX - 1][currY], downCeilId, q, used, currVertexDist + 1);
            }
        }
        if(a[finishId/n][finishId%n] == 0){
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
