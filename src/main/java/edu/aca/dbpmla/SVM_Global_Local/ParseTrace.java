package edu.aca.dbpmla.SVM_Global_Local;

import java.io.*;
import java.util.*;

/**
 * @author Dardan Xhymshiti
 * ParseTrace: parse traces from the given trace files
 */
public class ParseTrace 
{
	public ParseTrace(){}

/**
 * parse: parse the give trace file
 * @param trace_path: path to the trace file
 * @return: branches
 */
public ArrayList<Branch> parse(String trace_path)
{
	ArrayList<Branch> branch_list = new ArrayList<Branch>();
	String line="";
	
	try
	{	
		FileReader file_reader = new FileReader(trace_path);
		BufferedReader reader = new BufferedReader(file_reader);
				
		while((line = reader.readLine()) != null)
		{
			String[] split_line = line.split("\\s+");
			
			if(split_line[1].equals("T"))
				{
					branch_list.add(new Branch(split_line[0], 1));
				}
			else if(split_line[1].equals("N"))
				{
					branch_list.add(new Branch(split_line[0], 0));
				}
		}
		reader.close();
	}
	catch(Exception ex){ }

	System.out.println("FINISHED");
	return branch_list;
}
	
/**
 * doesBranchRepeat: checks and prints out the repeated branches
 * @param branch_list: the given branch list 
 */
public void doesBranchRepeat(ArrayList<Branch> branch_list)
	{
		for(int i=0; i<branch_list.size(); i++)
		{
			for(int j=0; j!=i && j<branch_list.size(); j++)
			{
				if(branch_list.get(i).equals(branch_list.get(j)))
				{
					
					System.out.println(branch_list.get(i).getAddress()+ " " + branch_list.get(i).getBranchDecicion() );
					
				}
			}
		}
	}
}
