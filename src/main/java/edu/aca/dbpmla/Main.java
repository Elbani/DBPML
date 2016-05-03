package edu.aca.dbpmla;

import edu.aca.dbpmla.lvq.LearningVectorQuantization;
import edu.aca.dbpmla.naivebayesian.predictor.NBImproved;
import edu.aca.dbpmla.naivebayesian.predictor.NBOriginalPredictor;
import edu.aca.dbpmla.naivebayesian.predictor.NBSetAssociativityPredictor;
import edu.aca.dbpmla.perceptron.predictorsImpl.GlobalLocalPerceptronImpl;
import edu.aca.dbpmla.perceptron.predictorsImpl.PerceptronPredictor;

/**
 * Created by Taulant on 5/1/2016.
 */
public class Main {
    public static void main(String[] args) {
        /**
         * To run the Predictors you must add two arguments:
         *
         * First: the path of the Trace file
         * Second: choose an algorithm (read below for more info)
         *
         *
         * Algorithms:
         *  0 = Perceptron Predictor
         *  1 = Perceptron with Global and Local History Predictor
         *  2 = LVQ Predictor
         *  3 = SVM with Global History Predictor
         *  4 = SVM with Global and Local History Predictor
         *  5 = Naive Bayesian with Global History Predictor
         *  6 = Naive Bayesian with Set Associativity Predictor
         *  7 = Naive Bayesian with Local History Predictor
         */
        switch (args[1]){
            case "0":
                PerceptronPredictor perceptronPredictor = new PerceptronPredictor();
                perceptronPredictor.runPerceptron(args[0]);
                break;
            case "1":
                GlobalLocalPerceptronImpl globalLocalPerceptron = new GlobalLocalPerceptronImpl();
                globalLocalPerceptron.runGlobalLocalPredictor(args[0]);
                break;
            case "2":
                LearningVectorQuantization learningVectorQuantization = new LearningVectorQuantization();
                learningVectorQuantization.runLVQ(args[0]);
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                NBOriginalPredictor nbOriginalPredictor = new NBOriginalPredictor();
                nbOriginalPredictor.runNBOriginal(args[0]);
                break;
            case "6":
                NBSetAssociativityPredictor nbSetAssociativityPredictor = new NBSetAssociativityPredictor();
                nbSetAssociativityPredictor.runNBSetAssociativity(args[0]);
                break;
            case "7":
                NBImproved nbImproved = new NBImproved();
                nbImproved.runNBImproved(args[0]);
                break;

        }
    }
}
