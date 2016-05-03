package edu.aca.dbpmla.svm.global;

import libsvm.*;

import java.util.ArrayList;

/**
 * Created by Dardan on 5/2/2016.
 */
public class G_SVM 
{
	public G_SVM(){}
	
	private static int true_prediction;
	private static int false_prediction;
	
	public svm_model trainModel(ArrayList<G_BranchHistoryEntry> bhe)
	{
		svm_problem prob = new svm_problem();
		int dataCount = bhe.size();
		
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];
		
		for(int i = 0; i < dataCount; i++)
		{
			G_BranchHistoryEntry features = bhe.get(i); //an entry full of branches
			prob.x[i] = new svm_node[features.getEntrySize()-1];
			
			for(int j = 1; j < features.getEntrySize(); j++)
			{
				svm_node node = new svm_node();
				node.index = j;
				node.value = features.getEntry().get(j);
				prob.x[i][j-1] = node;
			}
		prob.y[i] = features.getEntry().get(0); //get the label of a branch entry
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
	
	public void predictLabels(G_BranchHistoryEntry features, svm_model model)
	{
		
		svm_node[] nodes = new svm_node[features.getEntrySize()-1];
		
		for(int i = 1; i < features.getEntrySize(); i++)
		{
			svm_node node = new svm_node();
			node.index = i;
			node.value = features.getEntry().get(i);
			nodes[i-1] = node;
		}
		
		int classes = 2; //0 and 1
		int[] labels = new int[classes];
		svm.svm_get_labels(model, labels);
		
		double[] prob_estimates = new double[classes];
		double prediction = svm.svm_predict_probability(model, nodes, prob_estimates);
		double real_branch_outcome = features.getEntry().get(0);
			
		//System.out.println("Actual branch outcome: " + real_branch_outcome + "--> Prediction: " + prediction);
		
		if(real_branch_outcome == prediction)
		{
			true_prediction++;
		}
		else
		{
			false_prediction++;
		}
	}
	
	public int getTruePredictions()
	{
		return true_prediction;
	}
	
	public int getFalsePredictions()
	{
		return false_prediction;
	}
	
	
}

































