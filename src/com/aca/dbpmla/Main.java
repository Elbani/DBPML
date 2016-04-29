package com.aca.dbpmla;

import com.aca.dbpmla.entity.GlobalHistoryTable;
import com.aca.dbpmla.entity.LocalHistoryTable;
import com.aca.dbpmla.entity.PerceptronTable;
import com.aca.dbpmla.entity.Trace;
import com.aca.dbpmla.perceptronsImpl.PerceptronPredictor;
import com.aca.dbpmla.predictors.Predictor;
import com.aca.dbpmla.traces.TraceManager;
import com.aca.dbpmla.tracesImpl.TraceManagerImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Integer[] initials = new Integer[62];
        Arrays.fill(initials, 1);
        List<Integer> integers = Arrays.asList(initials);

        InputStream inputStream = System.in;

        if (args.length >= 1) {
            try {
                inputStream = new FileInputStream(args[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        TraceManager traceManager = new TraceManagerImpl();

        List<Trace> traceList = traceManager.readTrace(inputStream);

        GlobalHistoryTable globalHistoryTable = new GlobalHistoryTable();
        List<Integer> globalHistory = new LinkedList<>(integers);
        globalHistoryTable.setGlobalHistoryTable(globalHistory);

        PerceptronTable perceptronTable = new PerceptronTable();
        perceptronTable.setPerceptrons(new HashMap<>());

        Predictor perceptronPredictor = new PerceptronPredictor();

        perceptronTable.initializePerceptronTable(initials.length, perceptronTable.getPerceptrons());

        double total = 0.0;
        int accuratePredicted = 0;

        for(Trace trace : traceList) {
            if (!trace.getTakenNotBranch().equals("-")) {

                Long instructionAddress = trace.getInstructionAddress();

                double prediction = perceptronPredictor.predict(instructionAddress % 940, perceptronTable, globalHistoryTable);

                int t=-1;
                if (trace.getTakenNotBranch().equals("T")){
                    t = 1;
                }

                perceptronPredictor.train(instructionAddress % 940, prediction, t, perceptronTable, globalHistoryTable);

                boolean branchOutcome = false;
                if (trace.getTakenNotBranch().equals("T")) {
                    globalHistoryTable.getGlobalHistoryTable().remove(1);
                    globalHistoryTable.getGlobalHistoryTable().add(1);
                    branchOutcome = true;
                }
                else {
                    globalHistoryTable.getGlobalHistoryTable().remove(1);
                    globalHistoryTable.getGlobalHistoryTable().add(-1);
                }

                boolean perceptronPrediction = false;
                if(prediction>0)
                    perceptronPrediction = true;

                if(perceptronPrediction == branchOutcome)
                    accuratePredicted++;

                total = total + 1.0;


            }
        }
        System.out.println("Accuracy: " + accuratePredicted/total);

    }
}
