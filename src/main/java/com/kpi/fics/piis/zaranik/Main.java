package com.kpi.fics.piis.zaranik;

import com.kpi.fics.piis.zaranik.models.*;
import com.kpi.fics.piis.zaranik.models.algorithms.AStarPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.Algorithm;
import com.kpi.fics.piis.zaranik.models.algorithms.HeuristicFinder;
import com.kpi.fics.piis.zaranik.utils.MatrixIOUtil;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    private static int dist(int x1, int y1, int x2, int y2){
        return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
    }

    public static void main(String[] args) {
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Algorithm aStar = new AStarPerformer(new HeuristicFinder(Main::dist));
            Matrix matrix = MatrixIOUtil.readMatrix(fs);
            int stRow = 2, stCol = 9;
            int finRow = 8, finCol = 2;
            Matrix perform = aStar.perform(matrix, new Point(stRow, stCol), new Point(finRow, finCol));
            MatrixIOUtil.printToScreen(perform);
        } catch (IOException e) {
            System.out.println("reading was not successful:(");
        }

    }
}