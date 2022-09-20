package com.kpi.fics.piis.zaranik;

import com.kpi.fics.piis.zaranik.controllers.Controller;
import com.kpi.fics.piis.zaranik.models.algorithms.AStarPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.Algorithm;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;

public class MainAStart {

    public static void main(String[] args) {
        Controller controller = new Controller();
        Algorithm algorithm = new AStarPerformer(HeuristicCalculator::euclid);
        controller.start(algorithm);
    }

}