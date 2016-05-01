package edu.aca.dbpmla.perceptron;

import edu.aca.dbpmla.perceptron.predictorsImpl.GlobalLocalPerceptronImpl;

public class Main {

    public static void main(String[] args) {

        /*PerceptronPredictor perceptronPredictor = new PerceptronPredictor();
        perceptronPredictor.runPerceptron();*/

        GlobalLocalPerceptronImpl globalLocalPerceptron = new GlobalLocalPerceptronImpl();
        globalLocalPerceptron.runGlobalLocalPredictor();

    }
}
