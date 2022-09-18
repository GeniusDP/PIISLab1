package com.kpi.fics.piis.zaranik;

import com.kpi.fics.piis.zaranik.models.*;
import com.kpi.fics.piis.zaranik.models.algorithms.AStarPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.Algorithm;
import com.kpi.fics.piis.zaranik.models.algorithms.LiAlgorithmPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.NoWayFoundException;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.MatrixIOUtil;

import java.io.FileInputStream;
import java.io.IOException;

public class MainAStart {

    public static void main(String[] args) {
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Algorithm aStar = new AStarPerformer(HeuristicCalculator::manhattan);
            Matrix matrix = MatrixIOUtil.readMatrix(fs);

            int stRow = 2, stCol = 9;
            int finRow = 2, finCol = 2;
            Point startPoint = new Point(stRow, stCol);
            Point finishPoint = new Point(finRow, finCol);
            if (matrix.ceilIsObstacle(startPoint) || matrix.ceilIsObstacle(finishPoint)){
                throw new NoWayFoundException("start and finish ceils should not be obstacles");
            }

            Algorithm liAlgo = new LiAlgorithmPerformer(HeuristicCalculator::euclid);
            Matrix perform = liAlgo.perform(matrix, startPoint, finishPoint);
            //Matrix perform = aStar.perform(matrix, startPoint, finishPoint);
            MatrixIOUtil.printToScreen(perform);
        } catch (NoWayFoundException e) {
            System.out.println("Way was not found: " + e);
        } catch (IOException e) {
            System.out.println("reading was not successful:(");
        }


    }
}