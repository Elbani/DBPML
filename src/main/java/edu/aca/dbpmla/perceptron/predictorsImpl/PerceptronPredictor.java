package edu.aca.dbpmla.perceptron.predictorsImpl;

import edu.aca.dbpmla.perceptron.entity.GlobalHistoryTable;
import edu.aca.dbpmla.perceptron.entity.PerceptronTable;
import edu.aca.dbpmla.perceptron.entity.Trace;
import edu.aca.dbpmla.perceptron.predictors.Predictor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by elban on 4/23/16.
 */
public class PerceptronPredictor implements Predictor {
    @Override
    public double predict(Long address, PerceptronTable perceptronTable, GlobalHistoryTable historyTable) {

        double sum = 0.0;

        List<Double> perceptron = perceptronTable.getPerceptrons().get(address.intValue());
        List<Integer> history = historyTable.getGlobalHistoryTable();
        for(int i=1; i<historyTable.getGlobalHistoryTable().size(); i++){
            sum = sum + perceptron.get(i)*history.get(i);
        }

        return sum+1;
    }

    @Override
    public void train(Long address, double sum, int branchOutcome, PerceptronTable perceptronTable, GlobalHistoryTable globalHistoryTable) {
        List<Double> branchWeights = perceptronTable.getPerceptrons().get(address.intValue());
        List<Integer> globalHistory = globalHistoryTable.getGlobalHistoryTable();
        double theta = globalHistory.size() * 1.93 + 14;
        if(sum*branchOutcome<0 || Math.abs(sum) <= theta){
            for (int i = 0; i < branchWeights.size(); i++) {
                branchWeights.set(i, branchWeights.get(i) + branchOutcome*globalHistory.get(i));
            }
        }

    }

    public double runPerceptron(String traceLocation){
        Long startTime = System.nanoTime();

        List<Integer> integers = new LinkedList<>(Collections.nCopies(62, 1));
        double total = 0.0;
        int accuratelyPredicted = 0;

        InputStream inputStream = System.in;

        try {
            inputStream = new FileInputStream(traceLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GlobalHistoryTable globalHistoryTable = new GlobalHistoryTable();
        List<Integer> globalHistory = new LinkedList<>(integers);
        globalHistoryTable.setGlobalHistoryTable(globalHistory);

        PerceptronTable perceptronTable = new PerceptronTable();
        perceptronTable.setPerceptrons(new HashMap<>());

        Predictor perceptronPredictor = new PerceptronPredictor();

        perceptronTable.initializePerceptronTable(integers.size(), perceptronTable.getPerceptrons(), 940);

        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;

        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            String [] tokens = line.split("\\s+");
            Trace trace = new Trace();
            trace.setInstructionAddress(Long.parseLong(tokens[0], 16));
            trace.setTakenNotBranch(tokens[1]);

            Long instructionAddress = trace.getInstructionAddress();

            double prediction = perceptronPredictor.predict(instructionAddress % 940, perceptronTable, globalHistoryTable);

            int t = -1;
            if (trace.getTakenNotBranch().equals("T")) {
                t = 1;
            }

            perceptronPredictor.train(instructionAddress % 940, prediction, t, perceptronTable, globalHistoryTable);

            boolean branchOutcome = false;
            if (trace.getTakenNotBranch().equals("T")) {
                globalHistoryTable.getGlobalHistoryTable().remove(1);
                globalHistoryTable.getGlobalHistoryTable().add(1);
                branchOutcome = true;
            } else {
                globalHistoryTable.getGlobalHistoryTable().remove(1);
                globalHistoryTable.getGlobalHistoryTable().add(-1);
            }

            if (prediction > 0 == branchOutcome)
                accuratelyPredicted++;

            total = total + 1.0;
        }

        Long endTime = System.nanoTime();

        System.out.println("Time: " + (endTime - startTime));
        System.out.println("Accuracy: " + accuratelyPredicted / total);
        return accuratelyPredicted / total;
    }
}
