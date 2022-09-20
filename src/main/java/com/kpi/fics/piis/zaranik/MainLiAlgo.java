package com.kpi.fics.piis.zaranik;

import com.kpi.fics.piis.zaranik.controllers.Controller;
import com.kpi.fics.piis.zaranik.models.algorithms.Algorithm;
import com.kpi.fics.piis.zaranik.models.algorithms.LiAlgorithmPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;

public class MainLiAlgo {

    public static void main(String[] args) {
        Controller controller = new Controller();
        Algorithm algorithm = new LiAlgorithmPerformer(HeuristicCalculator::manhattan);
        controller.start(algorithm);
    }

}
