package edu.aca.dbpmla.svm.global;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Dardan on 5/2/2016.
 */
//==================================================================
						//ParseTrace class
//==================================================================
public class G_ParseTrace 
{
	public G_ParseTrace(){}

public ArrayList<Integer> parse(String trace_path)
{
	ArrayList<Integer> branch_list = new ArrayList<Integer>();
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
					branch_list.add(1);
					//System.out.println(split_line[0] + " " + split_line[1]);
				}
			else if(split_line[1].equals("N"))
				{
					branch_list.add(0);
					//System.out.println(split_line[0] + " " + split_line[1]);
				}
		}
		reader.close();
	}
	catch(Exception ex){ }

	//doesBranchRepead(branch_list);
	
	System.out.println("FINISHED");
	return branch_list;
}
	

//==================================================================
						//doesBranchRepeat
//==================================================================

//	public void doesBranchRepead(ArrayList<Integer> branch_list)
//	{
//		for(int i=0; i<branch_list.size(); i++)
//		{
//			for(int j=0; j!=i && j<branch_list.size(); j++)
//			{
//				if(branch_list.get(i).equals(branch_list.get(j)))
//				{
//					System.out.println(branch_list.get(i).getAddress());
//				}
//			}
//		}
//	}

//==================================================================
						//main
//==================================================================

//	public static void main(String[] args) 
//	{
//		String trace_path = "C:\\Users\\xhymshid\\Desktop\\gcc-10M-Branches.txt";
//		ParseTrace pt = new ParseTrace();
//		ArrayList<Integer> branch_list = pt.parse(trace_path);
//		System.out.println(branch_list);
//	}
}
