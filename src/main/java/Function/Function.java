package Function;
import java.util.List;

public class Function {
	/**
	 * A v�ltoz�k neveit tartalmazza.
	 */
   public List<String> variables;
   /**
    * Az elj�r�s neve.
    */
   public String name;
   /**
    * Az elj�r�s megh�v�sakor v�grehajtand� utas�t�sok string form�ban.
    */
     public   String commandStr;
    public Function(String name,List<String> variables, String commandStr){
        this.name=name;
        this.variables=variables;
        this.commandStr=commandStr;
    }


}
