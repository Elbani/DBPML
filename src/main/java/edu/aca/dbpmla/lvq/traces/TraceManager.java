package edu.aca.dbpmla.lvq.traces;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ardian on 4/29/2016.
 */
public class TraceManager {
    /**
     * Read the trace file.
     * @param tracePath
     * @return
     */
    public List<Trace> readTrace(String tracePath){

        List<Trace> traces = new ArrayList<>();
        try {
            traces = read(tracePath);
            //init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return traces;

    }

    /**
     * Read from file path.
     * @param tracePath
     * @return
     * @throws Exception
     */
    public List<Trace> read(String tracePath) throws Exception {
        File file = new File(tracePath);
        InputStream inputStream = new FileInputStream(file);
        PrintStream printStream = System.out;
        return process(inputStream, printStream);
    }

    /**
     * Parse trace file.
     * @param inputStream
     * @param printStream
     * @return
     * @throws Exception
     */
    private List<Trace> process(InputStream inputStream, PrintStream printStream) throws Exception {

        String line;
        List<Trace> tracesList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        printStream.format("Processing trace data... \n");
        while(true) {
            line = br.readLine();
            if(line == null) {
                break;
            }

            String[] tokens = line.split("\\s+");

            Trace trace = new Trace();

            String takenNTakenBranch = tokens[1];

            trace.setProgramCounter(Long.parseLong(tokens[0],16));


            if(!takenNTakenBranch.equals("-")) {

                int branchStatus;
                if(takenNTakenBranch.equals("T")) {
                    branchStatus = 1;
                } else {
                    branchStatus = 0;
                }

                trace.setTakenNTaken(branchStatus);

                tracesList.add(trace);

            }
        }
        printStream.format("Done processing!\n\n");
        return tracesList;
    }

    /**
     * Create input vector - PC(Program Counter k-bits) + Local History Register (n-bits) + Global History Register (n-bits)
     * @param globalHistoryRegister
     * @param localHistoryRegister
     * @param address
     * @param addressBitLength
     * @return
     */
    public int[] generateInputVector(List<Integer> globalHistoryRegister, List<Integer> localHistoryRegister, Long address, int addressBitLength){

        String X = "";
        int[] xArray = new int[globalHistoryRegister.size() + localHistoryRegister.size() + addressBitLength];

        int addressInt = Integer.parseInt(address.toString());
        String addressBinary = Integer.toBinaryString(addressInt);

        addressBinary = addressBinary.substring(addressBinary.length()-addressBitLength);

        X = X.concat(addressBinary);

        for (Integer globalHistoryBit : globalHistoryRegister) {
            X = X.concat("" + globalHistoryBit);
        }

        for (Integer localHistoryBit : localHistoryRegister) {
            X = X.concat("" + localHistoryBit);
        }

        char[] xBit = X.toCharArray();

        for(int i = 0 ; i<xBit.length ; i++){
            xArray[i] = Integer.parseInt(xBit[i]+"");
        }
        return xArray;
    }

}
