package edu.aca.dbpmla.predictorsImpl;

import edu.aca.dbpmla.entity.GlobalHistoryTable;
import edu.aca.dbpmla.entity.PerceptronTable;
import edu.aca.dbpmla.predictors.Predictor;

import java.util.List;

/**
 * Created by elban on 4/23/16.
 */
public class PerceptronPredictor implements Predictor {
    @Override
    public double predict(Long address, PerceptronTable perceptronTable, GlobalHistoryTable historyTable) {

        double sum = 0.0;

        List<Double> perceptron = perceptronTable.getPerceptrons().get(address.intValue());
        List<Integer> history = historyTable.getGlobalHistoryTable();
        for(int i=1; i<historyTable.getGlobalHistoryTable().size(); i++){
            sum = sum + perceptron.get(i)*history.get(i);
        }

        return sum+1;
    }

    @Override
    public void train(Long address, double sum, int branchOutcome, PerceptronTable perceptronTable, GlobalHistoryTable globalHistoryTable) {
        List<Double> branchWeights = perceptronTable.getPerceptrons().get(address.intValue());
        List<Integer> globalHistory = globalHistoryTable.getGlobalHistoryTable();
        double theta = globalHistory.size() * 1.93 + 14;
        if(sum*branchOutcome<0 || Math.abs(sum) <= theta){
            for (int i = 0; i < branchWeights.size(); i++) {
                branchWeights.set(i, branchWeights.get(i) + branchOutcome*globalHistory.get(i));
            }
        }

    }
}
