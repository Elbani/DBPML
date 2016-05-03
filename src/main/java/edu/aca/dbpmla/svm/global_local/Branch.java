package edu.aca.dbpmla.svm.global_local;
/**
 * @author Dardan Xhymshiti
 * Branch - This class models a branch with a given address and branch decision.
 */
public class Branch 
{
	private int address;
	private int decision;
		
	/**Constructor
	 * @param address: the address of the branch
	 * @param decision: the taken/not taken decision of the branch
	 */
	public Branch(int address, int decision)
	{
		this.address = address;
		this.decision = decision;
	}

	/**equals
	 * @param o: a given object
	 * @return: true if the given object is equal to this object
	 */
	public boolean equals(Object o)
	{
		boolean answer = false;
		
		if(o instanceof Branch)
		{
			if(this.address == (((Branch) o).address))
				answer = true;
		}
		return answer;
	}

	/** toString
	 * @return: a String representation of the class
	 */
	public String toString()
	{
		return address + " " + decision;
	}
		
	/**getAddress
	 * @return: the address of the branch
	 */
	public int getAddress()
	{
		return address;
	}

	/**setDecision: sets a taken/not taken decision to the branch
	 * @param dec: decision
	 */
	public void setBranchDecision(int dec)
	{ 
		decision = dec;
	}
		
	/**getBranchDecision
	 * @return: the branch taken/not taken decision 
	 */
	public int getBranchDecicion()
	{
		return decision;
	}
}
