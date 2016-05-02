/**
 * @author Dardan Xhymshiti
 * BranchLocalityManager - manages the branch prediction for a single branch
 */
import java.util.*;

public class BranchLocalityManager 
{	
	private HashMap<String, ArrayList<Integer>> locality = new HashMap<String, ArrayList<Integer>>(); //Address, decisions
	private SVM_Locality svm;
	private int entry_size = 4;

	public BranchLocalityManager(SVM_Locality svm)
	{
		this.svm = svm;
	}

	/**
	 * isBranchThere: checks if a branch exist in the local history
	 * @param b: the branch being tested
	 * @return: true if the branch is in the local history and there are more than 3 branch decisions for that branch
	 */
	public boolean isBranchThere(Branch b)
	{
		boolean answer = false;
		if(locality.get(b.getAddress()) != null )
		{
			locality.get(b.getAddress()).add(b.getBranchDecicion()); 
			if(locality.get(b.getAddress()).size()>3)
				answer = true;
		}
		else
		{
			locality.put(b.getAddress(), new ArrayList<Integer>()); //nese branchi nuk o, ateher shtoje ne locality hashmap
			locality.get(b.getAddress()).add(b.getBranchDecicion()); // shtoja addressen e tij
		}
		return answer;
	}
	
	/**
	 * predictLocalityBranchDecision: predicts the next outcome of the branch
	 * @param b: the given branch
	 * @return: the taken/not taken result
	 */
	public double predictLocalityBranchDecision(Branch b)
	{
		ArrayList<Integer> local_branch_decisions = locality.get(b.getAddress());
		double [][] history = getBranchLocalityHistory(local_branch_decisions, entry_size); 
		double prediction = svm.predictLocalLabels(b.getBranchDecicion(), svm.trainLocalModel(history));
		locality.get(b.getAddress()).add(b.getBranchDecicion());
		local_branch_decisions.add((int)prediction);
		return prediction;
	}
	
	/**
	 * getBranchLocality: gets the history of a single branch
	 * @param b: the given branch
	 * @return: history of the branch outcomes
	 */
	public ArrayList<Integer> getBranchLocality(Branch b)
	{
		return locality.get(b.getAddress());
	}
	
	/**
	 * getBranchLocalityHistory: gets the branch history, compatible with SVM training part
	 * @param branch_decisions: the history of the given branch
	 * @param entry_size: the size of the branch
	 * @return: the SVM compatible local branch history data
	 */
	private static double[][] getBranchLocalityHistory(ArrayList<Integer> branch_decisions, int entry_size)
	{   
		int row = branch_decisions.size() - entry_size+1; //10-4 = 6;
		
		double[][] history = new double[row][entry_size];
		
		for(int i=0; i< row; i++)
		{
			for(int j = history[0].length-1; j>=0; j--)
			{
				history[i][j] = branch_decisions.get(j);
			}
			branch_decisions.remove(0);
		}
		return history;
	}
}
