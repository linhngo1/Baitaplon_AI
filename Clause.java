import java.util.ArrayList;
import java.util.List;



public class Clause {

    String sentence;
    boolean parsed;
  

    Clause()
    {
        sentence=null;
        parsed=false;
       
    }

    Clause(String str)
    {
        sentence=new String(str);
        parsed=false;
        
    }

    public boolean getParsed()
    {
        return parsed;
    }

    public void setParsed()
    {
      this.parsed=true;
    }

    public String getString()
    {
        return this.sentence;
    }

    public void setString(String str)
    {
        this.sentence=str;
    }
}
