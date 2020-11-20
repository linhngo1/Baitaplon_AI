import java.util.ArrayList;
import java.util.List;


public class Variable {

	Character var;
	List<Clause> possibilities;
	
	Variable()
	{
		this.var=null;
		List<Clause>possibilities=new ArrayList<Clause>();
	}
	
	Variable(Character c)
	{
		this.var=c;
		List<Clause>possibilities=new ArrayList<Clause>();
	}
	
	public Character getVarChar()
	{
		return this.var;
	}
	
	public void setVarchar(Character c)
	{
		this.var=c;
	}
	/* no need to get and set for arraylist. Use as it is */
	
	
 }
