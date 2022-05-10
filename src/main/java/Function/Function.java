package Function;
import java.util.List;

public class Function {
	/**
	 * A változók neveit tartalmazza.
	 */
   public List<String> variables;
   /**
    * Az eljárás neve.
    */
   public String name;
   /**
    * Az eljárás meghívásakor végrehajtandó utasítások string formában.
    */
     public   String commandStr;
    public Function(String name,List<String> variables, String commandStr){
        this.name=name;
        this.variables=variables;
        this.commandStr=commandStr;
    }


}
