package edu.aca.dbpmla.lvq;

import edu.aca.dbpmla.lvq.branches.GlobalHistoryRegister;
import edu.aca.dbpmla.lvq.branches.LocalHistoryRegister;
import edu.aca.dbpmla.lvq.traces.Trace;
import edu.aca.dbpmla.lvq.traces.TraceManager;

import java.text.DecimalFormat;
import java.util.*;

public class LearningVectorQuantization {

    public double learningRateDecay(double learnRate) {
        double newLearnRate = learnRate / (1+learnRate);
        return newLearnRate;
    }

    public double[] train(double[] vw, int[] inputVector, boolean correctPrediction, double learningRate) {
        //Vw(t+1) = Vw(t) + a(t)[X(t) - Vw(t)]
        if(correctPrediction){
            for (int i = 0; i < inputVector.length; i++)
                vw[i] = vw[i] + learningRate * (inputVector[i] - vw[i]);
        }else{
            for (int i = 0; i < inputVector.length; i++)
                vw[i] = vw[i] - learningRate * (inputVector[i] - vw[i]);
        }
        return vw;
    }

    private double calculateHammingDistance(int[] inputVector, double[] weightVector) {
        double res = 0;
        for (int i = 0; i < inputVector.length; i++) {
            res += Math.pow(inputVector[i] - weightVector[i], 2);
        }
        return res;
    }



    public boolean predict(int[] inputVector, double[] vnt, double[] vt) {

        double inputVectorVntDistance = calculateHammingDistance(inputVector, vnt);
        double inputVectorVtDistance = calculateHammingDistance(inputVector, vt);

        if(inputVectorVntDistance < inputVectorVtDistance)
            return false;
        return true;
    }

    public double runLVQ(String traceLocation){
        //algorithm inputs
        int programCounterBits = 10;
        double learnRate = 0.01;
        int branchHistoryLength = 4; //branch history length, (k) bits of previous branches on Global History Register
        int localHistoryLength = 4; //local branch history length, (l) bits of previous recordings of particular branch in Local History Register

        long startTime = System.nanoTime();

        TraceManager traceManager = new TraceManager();

        List<Trace> traceList = null;
        try {
            traceList = traceManager.read(traceLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalHistoryRegister globalHistoryRegister = new GlobalHistoryRegister();
        List<Integer> globalHistory = new ArrayList<>(Collections.nCopies(branchHistoryLength, 1));

        LocalHistoryRegister localHistoryRegister = new LocalHistoryRegister();
        Map<Long, List<Integer>> localHistory = new HashMap<>();
        localHistoryRegister.setLocalHistory(localHistory);

        double[] vnt = new double[programCounterBits + branchHistoryLength + localHistoryLength];
        double[] vt = new double[programCounterBits + branchHistoryLength + localHistoryLength];

        Arrays.fill(vnt, 0.0);
        Arrays.fill(vt, 1.0);

        LearningVectorQuantization learningVectorQuantization = new LearningVectorQuantization();

        int correctPredictions = 0;
        double totalPredictions = 0.0;
        for(Trace trace : traceList){

            Long address = trace.getProgramCounter();

            if(localHistory.get(address)==null){
                localHistory.put(address, new ArrayList<>(Collections.nCopies(localHistoryLength,1)));
            }

            int[] x = traceManager.generateInputVector(globalHistory, localHistory.get(address), address, programCounterBits);

            boolean prediction = learningVectorQuantization.predict(x, vnt, vt);


            boolean branchOutcome = false;
            if(trace.getTakenNTaken()==1)
                branchOutcome = true;

            boolean correctPrediction = false;
            if(prediction == branchOutcome) {
                correctPrediction = true;
                correctPredictions++;

            }

            if(prediction){
                vt = learningVectorQuantization.train(vt, x, correctPrediction, learnRate);
            }else{
                vnt = learningVectorQuantization.train(vnt, x, correctPrediction, learnRate);
            }

            globalHistory.remove(0);
            globalHistory.add(trace.getTakenNTaken());

            localHistory.get(address).remove(0);
            localHistory.get(address).add(trace.getTakenNTaken());

            totalPredictions = totalPredictions + 1.0;

        }

        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime) / 1000000000.0;

        DecimalFormat df = new DecimalFormat("#.00");
        double percentage = (correctPredictions/totalPredictions) * 100;
        //System.out.println("Execution time: " + duration + "\n");
        System.out.println("False predictions:" + (int)(totalPredictions-correctPredictions));
        System.out.println("Correct predictions: " + correctPredictions);
        System.out.println("Total: " + (int)totalPredictions);
        System.out.println("Accuracy: " + df.format(percentage) + "%");
        //System.out.println(correctPredictions/totalPredictions);

        return percentage;
    }
}
