package variables;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Exception.InputErrorException;
class Func{
	private String name;
	private int num;
	public Func(String name,int num) {
		this.name=name;
		this.num=num;
	}
	public void addNum() {
		num++;
	}
	public void subNum() {
		num--;
	}
	public int getNum() {
		return num;
	}
	public String getName() {
		return name;
	}
}
public class VariableStorage {
    private static VariableStorage single_instance =null;
    /**
     * A program változói tartalmazó Map, melyben String és Double értékpárokat tárolok, ahol a Sztring a változó neve a Double pedig a változó értéke.
     */
    private Map<String,Double> variables=new HashMap<String,Double>();
    /**
     * A változó azt a számot tárolja ami a követlezõ ciklus változónak a szám részét fogja kiadni.
     */
    private int loopNumber=1;
    /**
     * Számon tartja, hogy éppen melyik eljárásban vagyunk és azon belül hanyadik ciklusnál járunk. A ciklusváltozó helyes elnevezésénél fontos.
     */
    private Stack<Func> funcs=new Stack<>();
    /**
     * Azt jelöli, hogy a program az elérés pillanatában eljáráshoz tartozó utasítást hajt-e végre.
     */
    /**
     * Annak az eljárás neve amibe a végrehajtott utasítás szerepel. A ciklusváltozóknál szükséges.
     */

    private VariableStorage(){}
    public static VariableStorage getInstance()
    {
        if (single_instance == null) {
            single_instance = new VariableStorage();
        }
        return single_instance;
    }
    /**Hozzáad egy változót a Map-be.
     * 
     * @param name A változó neve
     * @param value	A változó értéke
     * @param isLoop Loop változó-e?
     */
    public void addVariable(String name,Double value,boolean isLoop){
        variables.put(name,value);
        if(isLoop) {
            if (!funcs.isEmpty()) {
                funcs.peek().addNum();
            } else {
                loopNumber++;
            }
        }
    }
    /**
     *  Visszad a Map-bõl egy értéket a neve alapján.
     * @param name A változó neve.
     * @return
     */
    public Double getVariable(String name){
        return variables.get(name);
    }
    /**
     * Eltávolít egy bejegyzést a Map-bõl név alapján.
     * @param name A törlendõ változó neve.
     * @param isLoop	A törlendõ változó ciklusváltozó-e.
     */
    public void removeVariable(String name,boolean isLoop){
        variables.remove(name);
        if(isLoop) {
            if (!funcs.isEmpty()) {
            	
                funcs.peek().subNum();
            } else {
                loopNumber--;
            }
        }
    }
    public void list(){
        for (Map.Entry<String,Double> entry : variables.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
        System.out.println("--------");

    }
    /**
     * Attól függõen, hogy eljárásban vagyunk-e ad egy változó nevet, ami még nem foglalt.
     * @return A következõ ciklusnak adható szabad változó név.
     */
    public String giveLoopName(){
        if(!funcs.isEmpty()){
            return funcs.peek().getName()+"_l"+funcs.peek().getNum();
        }else{
            return "l"+loopNumber;
        }
    }
    /**
     * Frissít egy bejegyzést.
     * @param name A név ami alapján eldöntjük melyik változót frissítjük.
     * @param value	Az új érték.
     */
    public void updateVariable(String name,Double value){
        variables.replace(name,value);
    }
    /**
     * Minden bejegyzést kitöröl.
     */
    public void clear(){
        variables.clear();
        loopNumber=1;
    }
    /**
     * Beállítja, hogy eljárásban van-e és ha igen akkor melyikbe.
     * @param inFunc Eljárásban van e?
     * @param name Az eljárás neve.
     */
    public void setInFunc(boolean inFunc, String name) {
        if(inFunc) 
        {
        	funcs.push(new Func(name,1));
        	
        }else {
            if(!funcs.isEmpty())
        	    funcs.pop();
        }
    }
    public int getVariableCount(){
        return variables.size();
    }
}
