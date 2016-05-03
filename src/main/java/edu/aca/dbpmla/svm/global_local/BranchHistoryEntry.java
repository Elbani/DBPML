package edu.aca.dbpmla.svm.global_local;
/**
 * @author Dardan Xhymshiti
 * BranchHistoryEntry: models a branch history entry, with a predefined entry size
 * @param entry_size: defines how many branches this entry contains
 */
import java.util.*;

public class BranchHistoryEntry 
{
	private int entry_size;
	private ArrayList<Branch> branches; 
	
	public BranchHistoryEntry(int entry_size)
	{
		this.entry_size = entry_size;
		branches = new ArrayList<Branch>(entry_size);
	}
	
	/**
	 * addBranch - adds a branch to the entry, if there is no room for branche
	 * @param b: the given branch
	 */
	public void addBranch(Branch b)
	{
		if (branches.size() < entry_size)
			branches.add(b);
	}
	
	/**
	 * getEntry - gets the entry filled up with branches
	 */
	public ArrayList<Branch> getEntry()
	{
		return branches;
	}
	
	/**
	 * toString - represents the branch decisions in the entry 
	 */
	public String toString()
	{
		String s = "";
		for(Branch b: branches)
			s = s + b.getBranchDecicion()+ " ";
		return "Entry: " + s;
	}
	
	/**
	 * getEntrySize
	 * @return: the size of the entry 
	 */
	public int getEntrySize()
	{
		return entry_size;
	}
		
	/**
	 * riverseBranches: makes branches inside an entry compatible with SVM
	 * @param b: the entry
	 * @return: the reverse branch entry
	 */
	public ArrayList<Branch> riverseBranches(ArrayList<Branch> b)
	{
		ArrayList<Branch> reverse_branches = new ArrayList<Branch>();
		
		for(int i = b.size()-1; i>= 0; i--)
		{
			reverse_branches.add(b.get(i));
		}
		branches = reverse_branches;
		return reverse_branches;
	}
}
