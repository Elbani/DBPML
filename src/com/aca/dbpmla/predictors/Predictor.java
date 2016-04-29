package com.aca.dbpmla.predictors;

import com.aca.dbpmla.entity.GlobalHistoryTable;
import com.aca.dbpmla.entity.PerceptronTable;

/**
 * Created by elban on 4/23/16.
 */
public interface Predictor {
    double predict(Long address, PerceptronTable perceptronTable, GlobalHistoryTable historyTable);
    void train(Long address, double sum, int branchoutcome, PerceptronTable perceptronTable, GlobalHistoryTable globalHistoryTable);
}
