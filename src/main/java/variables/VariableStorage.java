package variables;

import java.util.ArrayDeque;
import java.util.Deque;
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
    private static VariableStorage singleInstance =null;
    /**
     * A program v�ltoz�i tartalmaz� Map, melyben String �s Double �rt�kp�rokat t�rolok, ahol a Sztring a v�ltoz� neve a Double pedig a v�ltoz� �rt�ke.
     */
    private Map<String,Double> variables=new HashMap<>();
    /**
     * A v�ltoz� azt a sz�mot t�rolja ami a k�vetlez� ciklus v�ltoz�nak a sz�m r�sz�t fogja kiadni.
     */
    private int loopNumber=1;
    /**
     * Sz�mon tartja, hogy �ppen melyik elj�r�sban vagyunk �s azon bel�l hanyadik ciklusn�l j�runk. A ciklusv�ltoz� helyes elnevez�s�n�l fontos.
     */
    private Deque<Func> funcs=new ArrayDeque<>();
    /**
     * Azt jel�li, hogy a program az el�r�s pillanat�ban elj�r�shoz tartoz� utas�t�st hajt-e v�gre.
     */
    /**
     * Annak az elj�r�s neve amibe a v�grehajtott utas�t�s szerepel. A ciklusv�ltoz�kn�l sz�ks�ges.
     */

    private VariableStorage(){}
    public static VariableStorage getInstance()
    {
        if (singleInstance == null) {
            singleInstance = new VariableStorage();
        }
        return singleInstance;
    }
    /**Hozz�ad egy v�ltoz�t a Map-be.
     * 
     * @param name A v�ltoz� neve
     * @param value	A v�ltoz� �rt�ke
     * @param isLoop Loop v�ltoz�-e?
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
     *  Visszad a Map-b�l egy �rt�ket a neve alapj�n.
     * @param name A v�ltoz� neve.
     * @return
     */
    public Double getVariable(String name){
        return variables.get(name);
    }
    /**
     * Elt�vol�t egy bejegyz�st a Map-b�l n�v alapj�n.
     * @param name A t�rlend� v�ltoz� neve.
     * @param isLoop	A t�rlend� v�ltoz� ciklusv�ltoz�-e.
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
     * Att�l f�gg�en, hogy elj�r�sban vagyunk-e ad egy v�ltoz� nevet, ami m�g nem foglalt.
     * @return A k�vetkez� ciklusnak adhat� szabad v�ltoz� n�v.
     */
    public String giveLoopName(){
        if(!funcs.isEmpty()){
            return funcs.peek().getName()+"_l"+funcs.peek().getNum();
        }else{
            return "l"+loopNumber;
        }
    }
    /**
     * Friss�t egy bejegyz�st.
     * @param name A n�v ami alapj�n eld�ntj�k melyik v�ltoz�t friss�tj�k.
     * @param value	Az �j �rt�k.
     */
    public void updateVariable(String name,Double value){
        variables.replace(name,value);
    }
    /**
     * Minden bejegyz�st kit�r�l.
     */
    public void clear(){
        variables.clear();
        loopNumber=1;
    }
    /**
     * Be�ll�tja, hogy elj�r�sban van-e �s ha igen akkor melyikbe.
     * @param inFunc Elj�r�sban van e?
     * @param name Az elj�r�s neve.
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
