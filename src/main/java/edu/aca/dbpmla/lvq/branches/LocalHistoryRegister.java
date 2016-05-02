package edu.aca.dbpmla.lvq.branches;

import java.util.*;

public class LocalHistoryRegister {

    private Map<Long, List<Integer>> localHistory;

    public Map<Long, List<Integer>> getLocalHistory() {
        return localHistory;
    }

    public void setLocalHistory(Map<Long, List<Integer>> localHistory) {
        this.localHistory = localHistory;
    }
}
