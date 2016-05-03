package edu.aca.dbpmla.common;

/**
 * Created by elban on 5/2/16.
 */
public class AlgorithmResults {
    private double predictionRate;
    private double mispredictionRate;
    private int truePredictions;
    private int falsePredictions;

    public double getPredictionRate() {
        return predictionRate;
    }

    public void setPredictionRate(double predictionRate) {
        this.predictionRate = predictionRate;
    }

    public double getMispredictionRate() {
        return mispredictionRate;
    }

    public void setMispredictionRate(double mispredictionRate) {
        this.mispredictionRate = mispredictionRate;
    }

    public int getTruePredictions() {
        return truePredictions;
    }

    public void setTruePredictions(int truePredictions) {
        this.truePredictions = truePredictions;
    }

    public int getFalsePredictions() {
        return falsePredictions;
    }

    public void setFalsePredictions(int falsePredictions) {
        this.falsePredictions = falsePredictions;
    }
}
