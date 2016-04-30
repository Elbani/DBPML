package edu.aca.dbpmla.perceptron.predictors;

import edu.aca.dbpmla.perceptron.entity.GlobalHistoryTable;
import edu.aca.dbpmla.perceptron.entity.PerceptronTable;

/**
 * Created by elban on 4/23/16.
 */
public interface Predictor {
    double predict(Long address, PerceptronTable perceptronTable, GlobalHistoryTable historyTable);
    void train(Long address, double sum, int branchoutcome, PerceptronTable perceptronTable, GlobalHistoryTable globalHistoryTable);
}
