package edu.aca.dbpmla.perceptron;

import edu.aca.dbpmla.perceptron.predictorsImpl.GlobalLocalPerceptronImpl;

public class Main {

    public static void main(String[] args) {
        GlobalLocalPerceptronImpl  globalLocalPerceptron = new GlobalLocalPerceptronImpl();
        globalLocalPerceptron.runGlobalLocalPredictor("/home/elban/Desktop/traces/gcc-1K-Branches.txt");

    }
}
