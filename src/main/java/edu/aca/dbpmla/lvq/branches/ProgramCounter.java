package edu.aca.dbpmla.lvq.branches;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ardian on 5/2/2016.
 */
public class ProgramCounter {
    private long address;
    private String binaryAddress;
    private String takenNTaken;
    private int takenInt;
    List<Integer> lastPredictions = new ArrayList<Integer>();

    public ProgramCounter() {

    }

    public ProgramCounter(long addr, String binAddr, String tn, int t, List<Integer> lastPred) {
        this.address = addr;
        this.binaryAddress = binAddr;
        this.takenNTaken = tn;
        this.takenInt = t;
        this.lastPredictions = lastPred;
    }

    public String convertToBinary(String hexAddress) {
        int i = Integer.parseInt(hexAddress, 16);
        String bin = Integer.toBinaryString(i);

        return bin;
    }

    public long getAddress() {
        return this.address;
    }

    public int getPrediction() {
        return this.takenInt;
    }

    public String getBinaryAddress() {
        return this.binaryAddress;
    }

    public List<Integer> getHistory() {
        return this.lastPredictions;
    }

    public void setHistory(List<Integer> res) {
        this.lastPredictions = res;
    }

    public void getAll() {

    }
}
