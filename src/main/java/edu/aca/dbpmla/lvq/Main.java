package edu.aca.dbpmla.lvq;

import edu.aca.dbpmla.lvq.branches.GlobalHistoryRegister;
import edu.aca.dbpmla.lvq.branches.LocalHistoryRegister;
import edu.aca.dbpmla.lvq.traces.Trace;
import edu.aca.dbpmla.lvq.traces.TraceManager;

import java.text.DecimalFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        //algorithm inputs
        int programCounterBits = 10;
        double learnRate = 0.01;
        int branchHistoryLength = 4; //branch history length, (k) bits of previous branches on Global History Register
        int localHistoryLength = 4; //local branch history length, (l) bits of previous recordings of particular branch in Local History Register

        //trace files
        String path = "C:\\Users\\ardian\\Desktop\\work\\java\\traces\\";
        String[] traces = {
                "art.txt", //0
                "gcc-1K-Branches.txt", //1
                "gcc-10M-Branches.txt", //2
                "go.txt", //3
                "gcc.txt", //4
                "hmmer.txt", //5
                "libquantum.txt", //6
                "mcf.txt", //7
                "sjeng.txt" //8
        };

        long startTime = System.nanoTime();

        TraceManager traceManager = new TraceManager();

        List<Trace> traceList = traceManager.read(path + traces[0]);

        GlobalHistoryRegister globalHistoryRegister = new GlobalHistoryRegister();
        List<Integer> globalHistory = new ArrayList<>(Collections.nCopies(branchHistoryLength,1));

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
    }
}
