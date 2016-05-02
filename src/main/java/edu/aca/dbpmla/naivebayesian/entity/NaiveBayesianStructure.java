package edu.aca.dbpmla.naivebayesian.entity;

import edu.aca.dbpmla.naivebayesian.predictor.NaiveBayesianImplementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Taulant on 5/1/2016.
 */
public class NaiveBayesianStructure extends NaiveBayesianImplementation {
    private List<Integer> historyTable;
    private Map<Integer, Integer> historyCountersP0, historyCountersP1;
    private int p1;
    private int historyBitLength;
    private int bitCounterLength;

    public NaiveBayesianStructure(int historyBitLength, int bitCounterLength) {
        this.historyTable = new ArrayList<>(Collections.nCopies(historyBitLength, 0));
        this.historyCountersP0 = generateCounters(historyTable.size());
        this.historyCountersP1 = generateCounters(historyTable.size());

        this.p1 = 0;
        this.historyBitLength = historyBitLength;
        this.bitCounterLength = bitCounterLength;
    }

    public List<Integer> getHistoryTable() {
        return historyTable;
    }

    public void setHistoryTable(List<Integer> historyTable) {
        this.historyTable = historyTable;
    }

    public Map<Integer, Integer> getHistoryCountersP0() {
        return historyCountersP0;
    }

    public void setHistoryCountersP0(Map<Integer, Integer> historyCountersP0) {
        this.historyCountersP0 = historyCountersP0;
    }

    public Map<Integer, Integer> getHistoryCountersP1() {
        return historyCountersP1;
    }

    public void setHistoryCountersP1(Map<Integer, Integer> historyCountersP1) {
        this.historyCountersP1 = historyCountersP1;
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getHistoryBitLength() {
        return historyBitLength;
    }

    public void setHistoryBitLength(int historyBitLength) {
        this.historyBitLength = historyBitLength;
    }

    public int getBitCounterLength() {
        return bitCounterLength;
    }

    public void setBitCounterLength(int bitCounterLength) {
        this.bitCounterLength = bitCounterLength;
    }
}
