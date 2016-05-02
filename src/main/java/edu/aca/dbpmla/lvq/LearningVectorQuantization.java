package edu.aca.dbpmla.lvq;

public class LearningVectorQuantization {

    public double learningRateDecay(double learnRate) {
        double newLearnRate = learnRate / (1+learnRate);
        return newLearnRate;
    }

    public double[] train(double[] vw, int[] inputVector, boolean correctPrediction, double learningRate) {
        //Vw(t+1) = Vw(t) + a(t)[X(t) - Vw(t)]
        if(correctPrediction){
            for (int i = 0; i < inputVector.length; i++)
                vw[i] = vw[i] + learningRate * (inputVector[i] - vw[i]);
        }else{
            for (int i = 0; i < inputVector.length; i++)
                vw[i] = vw[i] - learningRate * (inputVector[i] - vw[i]);
        }
        return vw;
    }

    private double calculateHammingDistance(int[] inputVector, double[] weightVector) {
        double res = 0;
        for (int i = 0; i < inputVector.length; i++) {
            res += Math.pow(inputVector[i] - weightVector[i], 2);
        }
        return res;
    }



    public boolean predict(int[] inputVector, double[] vnt, double[] vt) {

        double inputVectorVntDistance = calculateHammingDistance(inputVector, vnt);
        double inputVectorVtDistance = calculateHammingDistance(inputVector, vt);

        if(inputVectorVntDistance < inputVectorVtDistance)
            return false;
        return true;
    }

}
