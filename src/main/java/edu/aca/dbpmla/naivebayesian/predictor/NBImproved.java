package edu.aca.dbpmla.naivebayesian.predictor;

import edu.aca.dbpmla.naivebayesian.entity.NaiveBayesianStructure;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by Taulant on 5/2/2016.
 */
public class NBImproved extends NaiveBayesianImplementation {

    public double runNBImproved(String traceLocation) {
        int totalBranches = 0, truePredictions = 0, falsePredictions = 0;
        boolean prediction;
        NaiveBayesianStructure naiveBayesianStructure;
        HashMap<Integer, NaiveBayesianStructure> nbHashMapPredictor = new HashMap<>();


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
            int address = Integer.parseInt(tokens[0], 16);
            int branchStatus;
            if (tokens[1].equals("T")) {
                branchStatus = 1;
            } else {
                branchStatus = 0;
            }

            totalBranches++;

            if(nbHashMapPredictor.get(address) == null){
                naiveBayesianStructure = new NaiveBayesianStructure(10, 4);
                nbHashMapPredictor.put(address, naiveBayesianStructure);
            }
            prediction = predictBranch(nbHashMapPredictor, address);
            trainNaiveBayesian(branchStatus, nbHashMapPredictor, address);


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
