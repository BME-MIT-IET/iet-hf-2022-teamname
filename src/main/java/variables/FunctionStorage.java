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
	 * Az eljárásokat tartalmazó tároló. Azért ObservableList mivel így közvetlen be lehet állítani a megjelenítés ListViewének adatának.
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
     * Hozzáad egy eljárást.
     * @param f Az adott eljárás.
     */
    public void addFunction(Function f){
    	if(!this.hasFunction(f.name))
    		functions.add(f);
    }
    /**
     * Eltávolít egy eljárást Objekt alapján.
     * @param f Az eltávolítandó eljárás.
     */
    public void removeFunction(Function f) {
        functions.remove(f);
    }
    /**
     * Eltávolít egy eljárást név alapján.
     * @param name Az eltávolítandó elem neve.
     */
    public void removeFunctionByName(String name) {
        functions.removeIf(f -> f.name.equals(name));
    }
    /**
     * Visszadja a tároló listát.
     * @return Az eljárások listája.
     */
    public ObservableList<Function> getList(){
        return functions;
    }
    /**
     * Név alapján lineáris megkeres egy eljárást.
     * @param name A keresett eljárás neve.
     * @return Az eljárás.
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
    * Igaz értéket ad vissza, ha létezik eljárás a megadott névvel.
    * @param name A név amivel keresünk.
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
     * Elmenti az összes rendelkezésre álló eljárást a megadott json fájlba.
     * @param file A fájl helye ahova mentjük.
     * @throws IOException Ha nem valós helyet kapunk.
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
     * Betölti az összes eljárást a kapott json fájlból. Ha már van azzal a névvel egy elem akkor azt kihagyja.
     * @param file A fájl helye ahonnan beolvassuk.
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
