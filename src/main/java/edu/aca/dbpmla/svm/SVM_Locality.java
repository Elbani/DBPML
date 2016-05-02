package edu.aca.dbpmla.svm; /**
 * @author Dardan Xhymshiti
 * SVM_Locality: modified SVM algorithm to predict branch outcomes for a single branch
 */

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class SVM_Locality 
{
	public SVM_Locality(){}
	
	private static int true_prediction;
	private static int false_prediction;
	
	/**
	 * trainLocalModel: trains the model with the given data
	 * @param bhe: branch history entries
	 * @return: the trained model which knows how to predict
	 */
	public svm_model trainLocalModel(double[][] bhe)
	{
		svm_problem prob = new svm_problem();
		int dataCount = bhe.length;
		
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];
		
		for(int i = 0; i < dataCount; i++)
		{
			double[] features = bhe[i]; //an entry full of branches
			prob.x[i] = new svm_node[features.length-1];
			
			for(int j = 1; j < features.length; j++)
			{
				svm_node node = new svm_node();
				node.index = j;
				node.value = features[j];
				prob.x[i][j-1] = node;
			}
		prob.y[i] = features[0];
		}
		
		svm_parameter param = new svm_parameter();
		param.probability = 1;
	    param.gamma = 0.00001;
	    param.nu = 0.1;
	    param.C = 100;
	    param.svm_type = svm_parameter.LINEAR;
	    param.kernel_type = svm_parameter.LINEAR;       
	    param.cache_size = 20000;
	    param.eps = 0.001;      
		
	    svm_model model = svm.svm_train(prob, param);
		return model;
	}
	
	/**
	 * predictLocalLabels: predicts the outcome of a single branch
	 * @param features: the real outcome of the branch
	 * @param model: the trained model that knows how to predict
	 * @return: the predicted branch outcome
	 */
	public double predictLocalLabels(int features, svm_model model)
	{
		svm_node[] nodes = new svm_node[1];
		
		for(int i = 0; i < 1; i++)
		{
			svm_node node = new svm_node();
			node.index = i;
			node.value = features;
			nodes[i] = node;
		}
		
		int classes = 2; //0 and 1
		int[] labels = new int[classes];
		svm.svm_get_labels(model, labels);
		
		double[] prob_estimates = new double[classes];
		double prediction = svm.svm_predict_probability(model, nodes, prob_estimates);
		double real_branch_outcome = features;
			
		//System.out.println("Actual branch outcome: " + real_branch_outcome + "--> Prediction: " + prediction);
		
		if(real_branch_outcome == prediction)
		{
			true_prediction++;
		}
		else
		{
			false_prediction++;
		}
		return prediction;
	}
	
	/**
	 * getTruePredictions
	 * @return: how many times the algorithm predicted correctly
	 */
	public int getTruePredictions()
	{
		return true_prediction;
	}
	
	/**
	 * getFalsePredictions
	 * @return: how many times the algorithm predicted incorrectly
	 */
	public int getFalsePredictions()
	{
		return false_prediction;
	}
}

































