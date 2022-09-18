package com.kpi.fics.piis.zaranik;

import com.kpi.fics.piis.zaranik.models.*;
import com.kpi.fics.piis.zaranik.models.algorithms.AStarPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.Algorithm;
import com.kpi.fics.piis.zaranik.models.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.MatrixIOUtil;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Algorithm aStar = new AStarPerformer(HeuristicCalculator::euclid);
            Matrix matrix = MatrixIOUtil.readMatrix(fs);

            int stRow = 2, stCol = 9;
            int finRow = 2, finCol = 2;
            Matrix perform = aStar.perform(matrix, new Point(stRow, stCol), new Point(finRow, finCol));
            MatrixIOUtil.printToScreen(perform);
        } catch (IOException e) {
            System.out.println("reading was not successful:(");
        }

    }
}