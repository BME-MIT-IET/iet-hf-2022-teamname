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
	 * Az eljárásban lévõ utasítások ebbe a listába fognak kerülni.
	 */
   private List<Command> cmds=new ArrayList<>();
   /**
	 * Az adott eljáráshoz tartozó Function objektum.
	 */
    private Function func;
    /**
	 * Az eljáráskor bevitt változóértékeket tárolja melyek szétlettek bontva.
	 */
    private ArrayList<Token>[] vars;
    /**
	 * A Változókat tároló osztály.
	 */
    private VariableStorage varStr=VariableStorage.getInstance();
    /**
	 * A bemeneten megadott Token listát a megadott Token típus mentén szétválasztja.
	 * 
	 * @param tokens	A számokat tartalmazó Token sorozat.
	 * @param tt		Az adott token típus ami mentén az elválasztást el kell végezni.
	 * @return 			A tokenek szétválasztva a megadott elválasztó mentén. Listák tömbje.
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
    /**Az osztály konstruktora. Megkeresi a függvényt a függvénytárolóban, 
     *a parancsokat kiszûri, a változókat pedig feldarabolja/elõkészíti.
     * 
	 *@param name	A keresett eljárás neve.
	 *@param tokens	A kiértékelendõ részt tartalmazó tokenek.
	 */
    public FunctionExpression(String name,List<Token> tokens) throws InputErrorException {
        FunctionStorage funcStr =FunctionStorage.getInstance();
        func=funcStr.getFunction(name);
        Parser p=new Parser();
        cmds=p.parse(new Tokenizer(func.commandStr).getTokens());

       vars=split(tokens.subList(1, tokens.size()-1),TokenTypes.COMMA);

    }
    /**Az eljárásban található utasítás sorozatot lefutattja, a változókat elõtte be utána meg ki vesszi a tárolóból.
     * Értesíti a változó tárolót, hogy jelenleg egy függvény utasításai futnak.
     * 
	 *@param p	A játékban lévõ Poloska objektum.
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
