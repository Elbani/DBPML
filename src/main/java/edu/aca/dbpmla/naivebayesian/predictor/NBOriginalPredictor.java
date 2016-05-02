package edu.aca.dbpmla.naivebayesian.predictor;

import edu.aca.dbpmla.naivebayesian.entity.NaiveBayesianStructure;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by Taulant on 5/2/2016.
 */
public class NBOriginalPredictor extends NaiveBayesianImplementation {

    public double runNBOriginal(String traceLocation) {
        int totalBranches = 0, truePredictions = 0, falsePredictions = 0;
        boolean prediction;
        NaiveBayesianStructure naiveBayesianStructure = new NaiveBayesianStructure(10, 4);

        HashMap<Integer, NaiveBayesianStructure> nbHashMapPredictor = new HashMap<>();
        nbHashMapPredictor.put(0, naiveBayesianStructure);

        InputStream inputStream = System.in;

        try {
            inputStream = new FileInputStream(traceLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;

        long startTime = System.nanoTime();
        System.out.println("Processing...");
        while (true) {
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }

            String[] tokens = line.split("\\s+");
            int branchStatus;
            if (tokens[1].equals("T")) {
                branchStatus = 1;
            } else {
                branchStatus = 0;
            }

            totalBranches++;

            prediction = predictBranch(nbHashMapPredictor, 0);
            trainNaiveBayesian(branchStatus, nbHashMapPredictor, 0);


            if (prediction && branchStatus == 1 || !prediction && branchStatus == 0) {
                truePredictions++;
            } else {
                falsePredictions++;
            }
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println("TIME: " + duration);
        System.out.println("Total Branches: " + totalBranches);
        System.out.println("Total True Predictions: " + truePredictions);
        System.out.println("Total False Predictions: " + falsePredictions);

        DecimalFormat df2 = new DecimalFormat(".##");

        String accuracy = df2.format((double) truePredictions / totalBranches * 100);
        System.out.println("\n Accuracy : " + accuracy + "%");


        return (double) truePredictions / totalBranches;
    }
}
