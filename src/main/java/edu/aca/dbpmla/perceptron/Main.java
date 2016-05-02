package edu.aca.dbpmla.perceptron;

import edu.aca.dbpmla.perceptron.predictorsImpl.GlobalLocalPerceptronImpl;

public class Main {

    public static void main(String[] args) {
        GlobalLocalPerceptronImpl  globalLocalPerceptron = new GlobalLocalPerceptronImpl();
        globalLocalPerceptron.runGlobalLocalPredictor("D:\\CU Denver\\Semester II\\Advanced Computer Architecture\\Project\\Simulators\\Java Simulator\\New Traces\\gcc-1K-Branches");

    }
}
