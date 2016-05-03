package edu.aca.dbpmla.svm.global;

import edu.aca.dbpmla.common.AlgorithmResults;
import libsvm.svm_model;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dardan on 5/2/2016.
 */
public class G_GlobalBranchHistory
{
	private double accuracy;
	private int true_predictions;
	private int false_predictions;

	public G_GlobalBranchHistory(){}

	public AlgorithmResults run(String trace_path)
	{
		G_SVM svm = new G_SVM();
		AlgorithmResults algorithmResults = new AlgorithmResults();
		ArrayList<G_BranchHistoryEntry> gbh = new ArrayList<G_BranchHistoryEntry>();

		//=================================Traces===================================

		G_ParseTrace pt = new G_ParseTrace();
		ArrayList<Integer> branch_list = pt.parse(trace_path);
//		System.out.println(branch_list.size());
		//==========================================================================

		//===============================Training dataset===============================
		int entry_size = 37;
		int training_entries = 50; // how many branches you want to use for training
		int nr_entries = training_entries - entry_size; //40 entries for training ----branch_list.size()

		// training-----------------------------------------------
		for(int i=0; i<nr_entries; i++)
		{
			G_BranchHistoryEntry bhe = new G_BranchHistoryEntry(entry_size);
			for(int j=0; j<entry_size; j++)
			{
				bhe.addBranch(branch_list.get(j));
			}
			bhe.riverseBranches(bhe.getEntry());//reverse branches because SVM reads so.
			gbh.add(bhe);

			branch_list.remove(0);
		}

		svm_model model = svm.trainModel(gbh);
		System.out.println("entered");
		//==================================prediction==========================================

		int index = 0;
		int i = 0;
		int j = entry_size;

		while(index <branch_list.size()-entry_size)
		{

			G_BranchHistoryEntry entry = new G_BranchHistoryEntry(entry_size);
			for(int step=i; step < j; step++)
			{
				entry.addBranch(branch_list.get(step));
			}
			svm.predictLabels(entry, model); //prediction
			gbh.add(entry); // add the entry to the training data
			//model = svm.trainModel(gbh); //Here we have to retrain our algorithm.
			index++;
			i++;
			j++;
//			System.out.println(index);
		}


		accuracy = svm.getTruePredictions()/(double)(branch_list.size()-entry_size);
		true_predictions = svm.getTruePredictions();
		false_predictions = svm.getFalsePredictions();

		algorithmResults.setTruePredictions(true_predictions);
		algorithmResults.setFalsePredictions(false_predictions);
		algorithmResults.setPredictionRate(accuracy*100);
		algorithmResults.setMispredictionRate(100-accuracy*100);

        System.out.println("Total True Predictions: " + true_predictions);
        System.out.println("Total False Predictions: " + false_predictions);

        DecimalFormat df2 = new DecimalFormat(".##");

        String accuracys = df2.format( accuracy * 100);
        System.out.println("\n Accuracy : " + accuracys + "%");
        return algorithmResults;
	}
}
