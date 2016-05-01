package edu.aca.dbpmla.perceptron.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by elban on 5/1/16.
 */
public class GlobalLocalPerceptronTable {
    private Map<Long, List<Double>> perceptrons;

    public Map<Long, List<Double>> getPerceptrons() {
        return perceptrons;
    }

    public void setPerceptrons(Map<Long, List<Double>> perceptrons) {
        this.perceptrons = perceptrons;
    }

    public Map<Long, List<Double>> initializePerceptronTable(int numberOfWeights, Map<Long, List<Double>> perceptronTable, int numberOfPerceptrons){

        for(int k = 0; k<numberOfPerceptrons; k++) {
            List<Double> weights = new LinkedList<>();
            for (int i = 0; i < numberOfWeights; i++) {
                double randomWeight = Math.random();
                weights.add(randomWeight);
            }

            perceptronTable.put(Long.valueOf(k), weights);
        }
        return perceptronTable;
    }
}
