package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.utils.IntPair;
import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;

import java.util.*;

public class AStarPerformer implements Algorithm {
    private final HeuristicCalculator heuristicCalculator;
    private final HeuristicFinder heuristicFinder;

    public AStarPerformer(HeuristicCalculator heuristicCalculator) {
        if(heuristicCalculator == null){
            throw new IllegalArgumentException("heuristic should not be null!");
        }
        this.heuristicCalculator = heuristicCalculator;
        this.heuristicFinder = new HeuristicFinder(heuristicCalculator);
    }

    public AStarPerformer() {
        this.heuristicCalculator = HeuristicCalculator::manhattan;
        this.heuristicFinder = new HeuristicFinder(heuristicCalculator);
    }

    private boolean[] closed;
    private boolean[] isOpenNow;
    private int[] from;
    private int[] g;
    private int[] f;

    @Override
    public Matrix perform(Matrix matrix, Point start, Point finish) {
        if (!matrix.isPerformed()) {
            matrix.setPerformed(true);
            int[][] h = heuristicFinder.findHeuristic(matrix.getN(), matrix.getM());

            PriorityQueue<IntPair> open = new PriorityQueue<>();

            initArrays(matrix);

            int startId = start.getPointId(matrix.getN());
            int finishId = finish.getPointId(matrix.getN());
            g[startId] = 0;
            f[startId] = g[startId] + h[startId][finishId];
            isOpenNow[startId] = true;
            open.add(new IntPair(f[startId], startId));

            while (!open.isEmpty()) {
                int curr = open.peek().second;
                IntPair neighbour = convertSumFormToIndexes(curr, matrix.getN());
                int neighbour_i = neighbour.first;
                int neighbour_j = neighbour.second;

                //extracting current node from the heap
                open.remove();
                closed[curr] = true;

                relax(matrix.getArray(), curr, neighbour_i, neighbour_j - 1, open, matrix.getN(), finishId, h);
                relax(matrix.getArray(), curr, neighbour_i, neighbour_j + 1, open, matrix.getN(), finishId, h);
                relax(matrix.getArray(), curr, neighbour_i - 1, neighbour_j, open, matrix.getN(), finishId, h);
                relax(matrix.getArray(), curr, neighbour_i + 1, neighbour_j, open, matrix.getN(), finishId, h);

                relax(matrix.getArray(), curr, neighbour_i - 1, neighbour_j - 1, open, matrix.getN(), finishId, h);
                relax(matrix.getArray(), curr, neighbour_i - 1, neighbour_j + 1, open, matrix.getN(), finishId, h);
                relax(matrix.getArray(), curr, neighbour_i + 1, neighbour_j - 1, open, matrix.getN(), finishId, h);
                relax(matrix.getArray(), curr, neighbour_i + 1, neighbour_j + 1, open, matrix.getN(), finishId, h);

            }
            Matrix wayMatrix = findWayFromStartToFinish(from, matrix.getArray(), finishId);
            return wayMatrix;
        }
        throw new IllegalStateException("pathfinding algorithm has been performed to matrix");
    }

    private Matrix findWayFromStartToFinish(int[] parents, int[][] array, int finishId) {
        List<Integer> way = new ArrayList<>();
        int index = finishId;
        way.add(index);
        while (parents[index] != -1) {
            index = parents[index];
            way.add(index);
        }
        Collections.reverse(way);
        for (int i = 0; i < way.size(); i++) {
            int y = way.get(i) / array.length;
            int x = way.get(i) % array.length;
            if (array[y][x] == -1) {
                continue;
            }
            array[y][x] = i;
        }
        return new Matrix(array);
    }

    private void initArrays(Matrix matrix) {
        closed = new boolean[matrix.getN() * matrix.getM()];
        Arrays.fill(closed, false);

        isOpenNow = new boolean[matrix.getN() * matrix.getM()];
        Arrays.fill(closed, false);

        from = new int[matrix.getN() * matrix.getM()];
        Arrays.fill(from, -1);

        g = new int[matrix.getN() * matrix.getM()];
        Arrays.fill(g, INF);

        f = new int[matrix.getN() * matrix.getM()];
        Arrays.fill(f, INF);
    }


    private IntPair convertSumFormToIndexes(int sumForm, int n) {
        return new IntPair(sumForm / n, sumForm % n);
    }


    private void relax(int[][] array, int curr, int neighbour_i, int neighbour_j, PriorityQueue<IntPair> open, int n, int fin, int[][] h) {
        if (!closed[neighbour_i * n + neighbour_j] && array[neighbour_i][neighbour_j] != -1) {
            int temp_g = g[curr] + 1;
            if (!isOpenNow[neighbour_i * n + neighbour_j] || temp_g < g[neighbour_i * n + neighbour_j]) {
                from[neighbour_i * n + neighbour_j] = curr;
                g[neighbour_i * n + neighbour_j] = temp_g;
                f[neighbour_i * n + neighbour_j] = g[neighbour_i * n + neighbour_j] + h[neighbour_i * n + neighbour_j][fin];
            }

            if (!isOpenNow[neighbour_i * n + neighbour_j]) {
                open.add(new IntPair(f[neighbour_i * n + neighbour_j], neighbour_i * n + neighbour_j));
            }
        }
    }

}
