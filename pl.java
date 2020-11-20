import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class RunProgram
{
	 	 public void suydienLui_Alg()
	{
		int i;
		boolean ketqua;
		for(i=0;i<pl.xac_minhList.size();i++)
		{
			Variable c=new Variable(pl.xac_minhList.get(i).sentence.charAt(0));
			//pl.flagPrintOnlyOnce=0;
			ketqua= ketqua_SuyDienLui(c);
			/* cycle detection case still left */
			if(ketqua==false)
			{
				
				try
				{
					pl.output.write("NO");
					pl.output.newLine();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					pl.output.write("YES");
					pl.output.newLine();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			for(Clause clr: pl.menh_deList)
			{
				clr.parsed=false;
				
			} 
			
			
		} 
		try
		{
			pl.output.close();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}
	 
	 
	 
	public boolean check_DieuKien(String str)
 	{
 		String[] strArray;
 		String str1;
 		int i=0,index;
 		
 		List<Clause>dieu_kienList=new ArrayList<Clause>(); 
 		index=str.indexOf('-');
 		str1=str.substring(index+2);	
 		strArray=str1.split(",");
 		boolean result;
 		
 		for(String string:strArray)
 		{
 			Character c=string.charAt(0); 
 			
 			Clause clr=new Clause(c.toString());
 			//clr.setParsed();
 			dieu_kienList.add(clr); 
 			if( isFact(c))
 			{
 				
 				 	//as  NA; its a fact but continue the loop
 				dieu_kienList.get(i).setParsed();
 			}
 			else if(isCycle(c))
 			{
 				
 				} 
 			else
 			{
 				
 				Variable var=new Variable(c);
 				result=ketqua_SuyDienLui(var);
 				if(result==true)
 				{
 					dieu_kienList.get(i).setParsed();
 					//Clause of a premise satisfied
 				}
 				else
 				{
 					/*if(pl.flagPrintOnlyOnce==0)
 					{
 						System.out.println("N/A # N/A");
 						try
 						{//pl.logs.write("N/A # N/A ");
 						//pl.logs.newLine();
 						}catch(Exception e)
 						{
 							e.printStackTrace();
 						}
 						pl.flagPrintOnlyOnce=1;
 					} */
 					//printLogsRelevantRules("N/A");
 					//printLogsNewGoalIntroduced("N/A");
 					break;
 				}
 			}
 			i++;
 			
 		}	
 		for(Clause clause: dieu_kienList)
		{
			if(clause.getParsed()==false)
			return false;
		}
			return true;
 			
 	}
	
	public boolean ketqua_SuyDienLui(Variable c)
	{
		boolean result_lui=false;
		for(Clause clause: pl.menh_deList)
		{
			//clause.setParsed();
			Character c1=clause.getString().charAt(0);
			
			if(c1.equals(c.var))
			{
				
				result_lui=check_DieuKien(clause.getString());
				
			}
			
		}
		
		 
		return result_lui;
	}
	
	public boolean isFact(Character c)
	{
		for(Clause clause: pl.kb)
		{
			if(c.equals(clause.getString().charAt(0)))
			{
				return true;
			}
		}
		return false;
		
	}
	
	public boolean isCycle(Character c)
	{
		for(Clause clause: pl.menh_deList)
		{
			if(c.equals(clause.getString().charAt(0)))
			{
				if(clause.getParsed()==true)
					return true;
			}
		}
		return false;
	} 
	
	public void setClauseForCycle(String str)
	{
		for(Clause clause: pl.menh_deList)
		{
			if(str.equalsIgnoreCase(clause.getString()))
			{
				clause.setParsed();
			}
		}
	}
	
	public void startSuyDienTien(String kbFile,String qFile)
 	{
		String queryLine;
		pl.kb.clear();
		pl.menh_deList.clear();
		BufferedReader reader1;
		boolean result;
		String line;
		
		try
		{
			reader1=new BufferedReader(new FileReader(qFile));
			
			for(queryLine=reader1.readLine();queryLine!=null;queryLine=reader1.readLine())
			{
				
				
					BufferedReader reader=new BufferedReader(new FileReader(kbFile));
					for(line=reader.readLine();line!=null;line=reader.readLine())
					{
						/* lIST OF RULES */
						Clause c=new Clause(line);
						if(c.getString().length()!=1)
							pl.menh_deList.add(c);
						else
							pl.kb.add(c);
		             }
						
				result=suyDienTien(queryLine);
				if(result==true)
					pl.output.write("YES");
				else
					pl.output.write("NO");
				pl.output.newLine();
				
				/*for(Clause clr:pl.sentenceList)
					clr.parsed=false; */
				pl.kb.clear();
				pl.menh_deList.clear();
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 	}
	
	public boolean suyDienTien(String alpha)
	{
		//pl.sentenceList,pl.kb
		int index,loopCount=0;
		String str2,line;
		String conclusion="";
		
		
			
		while(loopCount!=pl.menh_deList.size()) // 
		{
			loopCount=0;
			for(Clause clause:pl.menh_deList)
			{
				loopCount++;
				String str=clause.getString(); 
				index=str.indexOf('-');
				str2=str.substring(index+2);
				if(check_not_in_Kb(str2)==true)
				{
					//continue
				}
				else
				{
					if(clause.parsed==true)
					{
						// do not add again
						conclusion="";
					}
					else
					{
						conclusion=them_vaoKB(clause);
						loopCount=0;	
						break;
					}
					
				}
			
			}
			if(conclusion.equalsIgnoreCase(alpha)) 
				return true;
		}
		return false;
	}
	
	public boolean check_not_in_Kb(String str)
	{
		boolean flag=true;
		String[] strArray=str.split(",");
		int count=strArray.length;
		int actualCount=0;
		for(Clause c:pl.kb)
		{
			for(int i=0;i<strArray.length;i++)
			{
				if(c.getString().equalsIgnoreCase(strArray[i]))
				{
					actualCount++;
				}
			}
		}
			if(actualCount==count)
			{
				// In KB
				flag=false;
				return false;
			}
		
		return flag;
	}
	
	public String them_vaoKB(Clause clause)
	{
		String str=clause.getString();
		Character c=str.charAt(0);
		
		pl.kb.add(new Clause(c.toString()));
		clause.setParsed();
		//printForwardLogs(clause,c.toString());
		return c.toString();
	}
	
}
 
 	
 	


public class pl {

	      public static BufferedReader reader;
	      public static BufferedWriter output;
	      public static List<Clause> menh_deList=new ArrayList<Clause>();
	      public static List<Clause> xac_minhList=new ArrayList<Clause>();
	      public static List<Clause> kb=new ArrayList<Clause>();
	      //public static List<Clause> backward=new ArrayList<Clause>();
	      //public static boolean backwardChainingGotYESFlag=false;
	      //public static int flagPrintOnlyOnce=0;
	      public static void main(String args[])
	      {
	          String line=null;
	          Integer option;
	          Scanner in;

	          try
	          {
	              reader=new BufferedReader(new FileReader(args[3]));
	              output=new BufferedWriter(new FileWriter(args[7]));
	              for(line=reader.readLine();line!=null;line=reader.readLine())
	              {
	                     /* lIST OF RULES */
	                  Clause c=new Clause(line); 
	                  if(c.getString().length()!=1)
	                  {
	                      menh_deList.add(c);
	                       //System.out.println("rules: "+c.getString());
	                  }
	                  else
	                  {
	                      kb.add(c);
	                       //System.out.println("Facts: "+c.getString());
	                  }
	              }
	              reader=new BufferedReader(new FileReader(args[5]));
	              for(line=reader.readLine();line!=null;line=reader.readLine())
	              {             /* list of query */
	                  Clause c=new Clause(line);
	                  xac_minhList.add(c);
	                 // System.out.println(c.getString());
	              }
	          }catch(Exception e)
	          {
	              System.out.println("Error(743) in FileRead:"+e.toString());
	          }

	          //System.out.println("Enter option:");
	          //in=new Scanner(System.in);
	          option=Integer.parseInt(args[1]);		//in.nextInt(); - 
	          RunProgram run=new RunProgram();
	          			
	          if(option==1)
	          {
	              System.out.println("Forward Chaining");
	              run.startSuyDienTien(args[3],args[5]);
	          }
	          else if(option==2)
	          {
	              System.out.println("Backward Chaining");
	              run.suydienLui_Alg();
	          }
	        //   else
	        //   {
	        //       System.out.println("Resolution");
	        //       run.startPlResolutionLogic(args[3],args[5]);
	        //   }
	          try
	          {output.close();
	          
	          }catch(Exception e)
	          {
	        	  e.printStackTrace();
	          }
	          
	      }

	}


