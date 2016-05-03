package edu.aca.dbpmla.svm.global;

import java.util.ArrayList;

/**
 * Created by Dardan on 5/2/2016.
 */
public class G_BranchHistoryEntry 
{
	private int entry_size;
	private ArrayList<Integer> branches; 
	
	public G_BranchHistoryEntry(int entry_size)
	{
		this.entry_size = entry_size;
		branches = new ArrayList<Integer>(entry_size);
	}
	
	/**
	 * addBranch - adds a branch to the entry
	 */
	public void addBranch(int b)
	{
		if (branches.size() < entry_size)
			branches.add(b);
	}
	
	/**
	 * getEntry - gets the entry filled up with branches
	 */
	public ArrayList<Integer> getEntry()
	{
		return branches;
	}
	
	/**
	 * toString - represents the branch decisions in the entry 
	 */
	public String toString()
	{
		String s = "";
		for(Integer b: branches)
			s = s + b + " ";
		return "Entry: " + s;
	}
	
	public int getEntrySize()
	{
		return entry_size;
	}
	
	public ArrayList<Integer> riverseBranches(ArrayList<Integer> b)
	{
		ArrayList<Integer> reverse_branches = new ArrayList<Integer>();
		
		for(int i = b.size()-1; i>= 0; i--)
		{
			reverse_branches.add(b.get(i));
		}
		branches = reverse_branches;
		return reverse_branches;
	}
	
	public void addListOfBranches(ArrayList<Integer> b)
	{
		if(b.size() == entry_size)
			branches = b;
	}
}
