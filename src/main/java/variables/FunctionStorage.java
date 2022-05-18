package variables;

import Function.Function;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FunctionStorage {
	/**
	 * Az elj�r�sokat tartalmaz� t�rol�. Az�rt ObservableList mivel �gy k�zvetlen be lehet �ll�tani a megjelen�t�s ListView�nek adat�nak.
	 */
    private ObservableList<Function> functions = FXCollections.observableArrayList();
    private static FunctionStorage singleInstance =null;
    private FunctionStorage(){}
    public static FunctionStorage getInstance()
    {
        if (singleInstance == null) {
            singleInstance = new FunctionStorage();
        }
        return singleInstance;
    }
    /**
     * Hozz�ad egy elj�r�st.
     * @param f Az adott elj�r�s.
     */
    public void addFunction(Function f){
    	if(!this.hasFunction(f.name))
    		functions.add(f);
    }
    /**
     * Elt�vol�t egy elj�r�st Objekt alapj�n.
     * @param f Az elt�vol�tand� elj�r�s.
     */
    public void removeFunction(Function f) {
        functions.remove(f);
    }
    /**
     * Elt�vol�t egy elj�r�st n�v alapj�n.
     * @param name Az elt�vol�tand� elem neve.
     */
    public void removeFunctionByName(String name) {
        functions.removeIf(f -> f.name.equals(name));
    }
    /**
     * Visszadja a t�rol� list�t.
     * @return Az elj�r�sok list�ja.
     */
    public ObservableList<Function> getList(){
        return functions;
    }
    /**
     * N�v alapj�n line�ris megkeres egy elj�r�st.
     * @param name A keresett elj�r�s neve.
     * @return Az elj�r�s.
     */
   public Function getFunction(String name){
        for (Function f:functions){
            if(f.name.equalsIgnoreCase(name)){
                return f;
            }
        }
        return null;
   }
   /**
    * Igaz �rt�ket ad vissza, ha l�tezik elj�r�s a megadott n�vvel.
    * @param name A n�v amivel keres�nk.
    * @return
    */
    public boolean hasFunction(String name){
        for (Function f:functions){
            if(f.name.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Elmenti az �sszes rendelkez�sre �ll� elj�r�st a megadott json f�jlba.
     * @param file A f�jl helye ahova mentj�k.
     * @throws IOException Ha nem val�s helyet kapunk.
     */
    public void save(String file)throws IOException {
        Gson gson=new Gson();
        FileWriter writer = null;
        try {
	        writer =new FileWriter(file);
	        writer.write(gson.toJson(functions));
	        writer.flush();
        } finally {
        	writer.close();
        }
    }
    /**
     * Bet�lti az �sszes elj�r�st a kapott json f�jlb�l. Ha m�r van azzal a n�vvel egy elem akkor azt kihagyja.
     * @param file A f�jl helye ahonnan beolvassuk.
     * @throws IOException
     */
    public void load(String file)throws IOException {
        Gson gson=new Gson();
        BufferedReader reader =new BufferedReader(new FileReader(file));
        Type listType = new TypeToken<ArrayList<Function>>(){}.getType();
        List<Function> temp = new ArrayList<>(gson.fromJson(reader, listType));

        for(Function f: temp){
            if(!hasFunction(f.name)){
                functions.add(f);
            }
        }

        reader.close();
    }
}
