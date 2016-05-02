package edu.aca.dbpmla.perceptron.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by elban on 4/23/16.
 */
public class PerceptronTable {
    private Map<Integer, List<Double>> perceptrons;

    public Map<Integer, List<Double>> getPerceptrons() {
        return perceptrons;
    }

    public void setPerceptrons(Map<Integer, List<Double>> perceptrons) {
        this.perceptrons = perceptrons;
    }

    public Map<Integer, List<Double>> initializePerceptronTable(int numberOfWeights, Map<Integer, List<Double>> perceptronTable, int numberOfPerceptrons){

        for(int k = 0; k<numberOfPerceptrons; k++) {
            List<Double> weights = new LinkedList<>();
            for (int i = 0; i < numberOfWeights; i++) {
                double randomWeight = Math.random();
                weights.add(randomWeight);
            }

            perceptronTable.put(k, weights);
        }
        return perceptronTable;
    }


}
