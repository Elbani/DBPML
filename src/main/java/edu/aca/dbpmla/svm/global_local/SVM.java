package edu.aca.dbpmla.svm.global_local;
/**
 * @author Dardan Xhymshiti
 * SVM: the machine learning algorithm used to predict global branch outcomes
 */
import java.util.ArrayList;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class SVM 
{
	public SVM(){}
	
	private int true_prediction;
	private int false_prediction;
	
	/**
	 * trainModel: trains the prediction model based on the given data
	 * @param bhe: the given branch history entries 
	 * @return: the trained model that knows how to predict
	 */
	public svm_model trainModel(ArrayList<BranchHistoryEntry> bhe)
	{
		svm_problem prob = new svm_problem();
		int dataCount = bhe.size();
		
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];
		
		for(int i = 0; i < dataCount; i++)
		{
			BranchHistoryEntry features = bhe.get(i); //an entry full of branches
			prob.x[i] = new svm_node[features.getEntrySize()-1];
			
			for(int j = 1; j < features.getEntrySize(); j++)
			{
				svm_node node = new svm_node();
				node.index = j;
				node.value = features.getEntry().get(j).getBranchDecicion();
				prob.x[i][j-1] = node;
			}
		prob.y[i] = features.getEntry().get(0).getBranchDecicion(); //get the label of a branch entry
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
	 * predictLabels: predicts the global outcomes of branches
	 * @param features: the given branch entry
	 * @param model: the trained model that knows how to predict
	 */
	public void predictLabels(BranchHistoryEntry features, svm_model model)
	{		
		svm_node[] nodes = new svm_node[features.getEntrySize()-1];
		
		for(int i = 1; i < features.getEntrySize(); i++)
		{
			svm_node node = new svm_node();
			node.index = i;
			node.value = features.getEntry().get(i).getBranchDecicion();
			nodes[i-1] = node;
		}
		
		int classes = 2; //0 and 1
		int[] labels = new int[classes];
		svm.svm_get_labels(model, labels);
		
		double[] prob_estimates = new double[classes];
		double prediction = svm.svm_predict_probability(model, nodes, prob_estimates);
		double real_branch_outcome = features.getEntry().get(0).getBranchDecicion();
			
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

































