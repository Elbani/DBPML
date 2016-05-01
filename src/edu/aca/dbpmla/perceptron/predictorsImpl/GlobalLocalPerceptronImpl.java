package edu.aca.dbpmla.perceptron.predictorsImpl;

import edu.aca.dbpmla.perceptron.entity.GlobalHistoryTable;
import edu.aca.dbpmla.perceptron.entity.GlobalLocalPerceptronTable;
import edu.aca.dbpmla.perceptron.entity.LocalHistoryTable;
import edu.aca.dbpmla.perceptron.entity.Trace;

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
import java.util.Map;

/**
 * Created by elban on 4/30/16.
 */
public class GlobalLocalPerceptronImpl{

    public double predict(Long address, GlobalLocalPerceptronTable perceptronTable, GlobalHistoryTable globalHistoryTable, LocalHistoryTable localHistoryTable, List<Long> indexList) {

        int numberOfWeights = 16;
        int numberOfLocalHistoryBits = numberOfWeights/2;

        List<Integer> localHistory = localHistoryTable.getLocalHistoryTable().get(address);

        indexList.add(address % perceptronTable.getPerceptrons().size());
        double y = perceptronTable.getPerceptrons().get(indexList.get(0)).get(0);
        for (int i = 1; i < numberOfWeights; i++) {
            if(i<numberOfLocalHistoryBits){
               indexList.add((localHistory.get(i-1) ^ address) % perceptronTable.getPerceptrons().size());
                y = y + perceptronTable.getPerceptrons().get(indexList.get(i)).get(i);
            }else{
                indexList.add((globalHistoryTable.getGlobalHistoryTable().get(i-numberOfLocalHistoryBits) ^ address) % perceptronTable.getPerceptrons().size());
                y = y + perceptronTable.getPerceptrons().get(indexList.get(i)).get(i);
            }
        }

        return y;
    }

    public void train(double sum, boolean branchoutcome, GlobalLocalPerceptronTable perceptronTable, boolean correctPrediction, double theta, List<Long> indexList) {
        if (!correctPrediction || sum < theta) {
            for (int i = 0; i < indexList.size(); i++) {
                if (branchoutcome) {
                    perceptronTable.getPerceptrons().get(indexList.get(i)).set(i, perceptronTable.getPerceptrons().get(indexList.get(i)).get(i) + 1);
                } else {
                    perceptronTable.getPerceptrons().get(indexList.get(i)).set(i, perceptronTable.getPerceptrons().get(indexList.get(i)).get(i) - 1);
                }
            }
        }
    }

    public void runGlobalLocalPredictor(String traceLocation){

        int numberOfPerceptrons = 8192;

        Long startTime = System.nanoTime();

        List<Integer> integers = new LinkedList<>(Collections.nCopies(16, 1));
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

        LocalHistoryTable localHistoryTable = new LocalHistoryTable();
        Map<Long, List<Integer>> localHistory = new HashMap<>();
        localHistoryTable.setLocalHistoryTable(localHistory);

        GlobalLocalPerceptronTable perceptronTable = new GlobalLocalPerceptronTable();
        perceptronTable.setPerceptrons(new HashMap<>());

        GlobalLocalPerceptronImpl globalLocalPerceptron = new GlobalLocalPerceptronImpl();

        perceptronTable.initializePerceptronTable(integers.size(), perceptronTable.getPerceptrons(), numberOfPerceptrons);

        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;

        double theta = 1.93*integers.size() + integers.size()/2;

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

            List<Long> indexList = new LinkedList<>();

            if(localHistory.get(instructionAddress) == null)
                localHistory.put(instructionAddress, new LinkedList<>(integers));

            double y = globalLocalPerceptron.predict(instructionAddress, perceptronTable, globalHistoryTable, localHistoryTable, indexList);

            boolean branchOutcome = false;
            if (trace.getTakenNotBranch().equals("T")) {
                branchOutcome = true;
            }

            boolean predicion = false;
            if(y>0)
                predicion = true;

            boolean correctPrediction = false;
            if(branchOutcome == predicion)
                correctPrediction = true;

            globalLocalPerceptron.train(y, branchOutcome, perceptronTable , correctPrediction, theta, indexList);

            if (trace.getTakenNotBranch().equals("T")) {
                globalHistoryTable.getGlobalHistoryTable().remove(0);
                globalHistoryTable.getGlobalHistoryTable().add(1);
                localHistory.get(instructionAddress).remove(0);
                localHistory.get(instructionAddress).add(1);
            } else {
                globalHistoryTable.getGlobalHistoryTable().remove(0);
                globalHistoryTable.getGlobalHistoryTable().add(0);
                localHistory.get(instructionAddress).remove(0);
                localHistory.get(instructionAddress).add(0);
            }

            if (predicion == branchOutcome)
                accuratelyPredicted++;

            total = total + 1.0;
        }

        Long endTime = System.nanoTime();

        System.out.println("Time: " + (endTime - startTime));
        System.out.println("Accuracy: " + accuratelyPredicted / total);
    }
}
