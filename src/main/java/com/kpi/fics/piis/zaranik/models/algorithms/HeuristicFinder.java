package com.kpi.fics.piis.zaranik.models.algorithms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static java.lang.Math.abs;

@NoArgsConstructor
@AllArgsConstructor
public class HeuristicFinder {
    private HeuristicCalculator hc = (x1, y1, x2, y2) -> abs(x1 - x2) + abs(y1 - y2);

    public int[][] findHeuristic(int n, int m) {
        int[][] h = new int[n * n + m][n * n + m];

        for (int x1 = 0; x1 < n; x1++) {
            for (int y1 = 0; y1 < m; y1++) {
                for (int x2 = 0; x2 < n; x2++) {
                    for (int y2 = 0; y2 < m; y2++) {
                        int dist = hc.compute(x1, y1, x2, y2);
                        h[x1 * n + y1][x2 * n + y2] = dist;
                    }
                }
            }
        }
        return h;
    }

}
