package Commands;

import Function.Function;
import InputTranslator.*;
import Poloska.Poloska;
import variables.FunctionStorage;
import Exception.InputErrorException;
import variables.VariableStorage;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpression implements Command{
	/**
	 * Az elj�r�sban l�v� utas�t�sok ebbe a list�ba fognak ker�lni.
	 */
   private List<Command> cmds=new ArrayList<>();
   /**
	 * Az adott elj�r�shoz tartoz� Function objektum.
	 */
    private Function func;
    /**
	 * Az elj�r�skor bevitt v�ltoz��rt�keket t�rolja melyek sz�tlettek bontva.
	 */
    private ArrayList<Token>[] vars;
    /**
	 * A V�ltoz�kat t�rol� oszt�ly.
	 */
    private VariableStorage varStr=VariableStorage.getInstance();
    /**
	 * A bemeneten megadott Token list�t a megadott Token t�pus ment�n sz�tv�lasztja.
	 * 
	 * @param tokens	A sz�mokat tartalmaz� Token sorozat.
	 * @param tt		Az adott token t�pus ami ment�n az elv�laszt�st el kell v�gezni.
	 * @return 			A tokenek sz�tv�lasztva a megadott elv�laszt� ment�n. List�k t�mbje.
	 */
    private ArrayList<Token>[] split(List<Token> tokens, TokenTypes tt){
        int k=0;
        ArrayList<Token>[] sep=new ArrayList[func.variables.size()];
        for (int i = 0; i < func.variables.size(); i++) {
            sep[i] = new ArrayList<>();
        }
        for(Token t:tokens){
            if(t.getType()==tt){
                k++;
            }else{
                sep[k].add(t);
            }
        }
        return sep;
    }
    /**Az oszt�ly konstruktora. Megkeresi a f�ggv�nyt a f�ggv�nyt�rol�ban, 
     *a parancsokat kisz�ri, a v�ltoz�kat pedig feldarabolja/el�k�sz�ti.
     * 
	 *@param name	A keresett elj�r�s neve.
	 *@param tokens	A ki�rt�kelend� r�szt tartalmaz� tokenek.
	 */
    public FunctionExpression(String name,List<Token> tokens) throws InputErrorException {
        FunctionStorage funcStr =FunctionStorage.getInstance();
        func=funcStr.getFunction(name);
        Parser p=new Parser();
        cmds=p.parse(new Tokenizer(func.commandStr).getTokens());

       vars=split(tokens.subList(1, tokens.size()-1),TokenTypes.COMMA);

    }
    /**Az elj�r�sban tal�lhat� utas�t�s sorozatot lefutattja, a v�ltoz�kat el�tte be ut�na meg ki vesszi a t�rol�b�l.
     * �rtes�ti a v�ltoz� t�rol�t, hogy jelenleg egy f�ggv�ny utas�t�sai futnak.
     * 
	 *@param p	A j�t�kban l�v� Poloska objektum.
	 */
    @Override
    public void execute(Poloska p) throws InputErrorException{
        varStr.setInFunc(true,func.name);
        ShuntingYardParser shuni=new ShuntingYardParser();
        for (int i = 0; i <func.variables.size() ; i++) {
            varStr.addVariable(func.variables.get(i),shuni.convertTokenToAST(vars[i]).evaluate(),false);
        }
        varStr.list();
        for(Command cmd: cmds){
            cmd.execute(p);
        }

        for (int i = 0; i <func.variables.size() ; i++) {
            varStr.removeVariable(func.variables.get(i),false);
        }
        varStr.setInFunc(false, func.name);
    }
}
