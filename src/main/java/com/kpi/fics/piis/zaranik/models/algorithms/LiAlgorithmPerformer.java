package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LiAlgorithmPerformer extends AbstractAlgorithm {

    public LiAlgorithmPerformer(HeuristicCalculator heuristicCalculator) {
        super(heuristicCalculator);
    }

    @Override
    public Matrix perform(Matrix matrix, Point start, Point finish) {
        int[][] h = heuristicFinder.findHeuristic(matrix.getN(), matrix.getM());

        return null;
    }

}
