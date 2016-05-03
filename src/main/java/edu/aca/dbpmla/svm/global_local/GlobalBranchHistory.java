package edu.aca.dbpmla.svm.global_local;
/**
 * @author Dardan Xhymshiti
 * GlobalBranchHistory: simulates the work of the Global Branch History
 */
import java.util.ArrayList;

import edu.aca.dbpmla.common.AlgorithmResults;
import libsvm.svm_model;

public class GlobalBranchHistory 
{
	private double accuracy;
	private int true_predictions;
	private int false_predicions;
	
	public GlobalBranchHistory(){}
	
	/**
	 * run: runs the simulation
	 * @param trace_path: the path of the trace file
	 */
	public AlgorithmResults run(String trace_path)
	{
		AlgorithmResults algorithmResults = new AlgorithmResults();
		System.out.println("Process is being initiated...");
		SVM svm = new SVM();
		SVM_Locality svm_locality = new SVM_Locality();
		BranchLocalityManager blm = new BranchLocalityManager(svm_locality);
		ArrayList<BranchHistoryEntry> gbh = new ArrayList<BranchHistoryEntry>();
		
		//=================================Traces===================================
		ParseTrace pt = new ParseTrace();
		ArrayList<Branch> branch_list = pt.parse(trace_path);
		//System.out.println(branch_list.size());
		//==========================================================================
		
		
		//===============================Training dataset===========================
		int entry_size = 37;
		int training_entries = 50; // how many branches you want to use for training
		int nr_entries = training_entries - entry_size;  
		
		// training-----------------------------------------------	
		for(int i=0; i<nr_entries; i++)
		{	
			BranchHistoryEntry bhe = new BranchHistoryEntry(entry_size);
			for(int j=0; j<entry_size; j++)
			{
				bhe.addBranch(branch_list.get(j));
			}
			bhe.riverseBranches(bhe.getEntry());//reverse branches because SVM reads so.
			gbh.add(bhe);
			
			branch_list.remove(0);
		}
		
		//==============================Testing dataset=============================
				
		svm_model model = svm.trainModel(gbh);
		int index = 0;
		int i = 0;
		int j = entry_size;
		
		while(index <branch_list.size()-entry_size)
		{
			BranchHistoryEntry entry = new BranchHistoryEntry(entry_size);
			for(int step=i; step < j; step++)
			{
				entry.addBranch(branch_list.get(step));
			}
			
			if(blm.isBranchThere(branch_list.get(index)))
			{
				blm.predictLocalityBranchDecision(branch_list.get(index));
			}
			else
			{
				svm.predictLabels(entry, model); //prediction	
			}
			gbh.add(entry);
			//model = svm.trainModel(gbh); //Here we have to retrain our algorithm.
			index++;
			i++;
			j++;
			//System.out.println(index);
		}
		
		System.out.println("finished");
		
		true_predictions = svm.getTruePredictions() + svm_locality.getTruePredictions();
		false_predicions = svm.getFalsePredictions() + svm_locality.getFalsePredictions();
		accuracy = true_predictions/(double)(branch_list.size()-entry_size);

        algorithmResults.setTruePredictions(true_predictions);
        algorithmResults.setFalsePredictions(false_predicions);
        algorithmResults.setPredictionRate(accuracy*100);
        algorithmResults.setMispredictionRate(100-accuracy);
        return algorithmResults;
	}
}
