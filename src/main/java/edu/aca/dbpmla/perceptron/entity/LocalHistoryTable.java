package edu.aca.dbpmla.perceptron.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by elban on 4/24/16.
 */
public class LocalHistoryTable {
    private Map<Long, List<Integer>> localHistoryTable;

    public Map<Long, List<Integer>> getLocalHistoryTable() {
        return localHistoryTable;
    }

    public void setLocalHistoryTable(Map<Long, List<Integer>> localHistoryTable) {
        this.localHistoryTable = localHistoryTable;
    }


}
