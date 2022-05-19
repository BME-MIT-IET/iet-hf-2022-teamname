package Commands;

import InputTranslator.Parser;
import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import InputTranslator.TokenTypes;
import Nodes.ASTNode;
import Poloska.Poloska;
import variables.VariableStorage;
import Exception.InputErrorException;
import java.util.ArrayList;
import java.util.List;

public class LoopExpression implements Command{
	/**
	 * A ciklusszámot reprezentáló AST.
	 */
    private ASTNode number;
    /**
     * A ciklus belsejében lévõ utasítások.
     */
    private ArrayList<Command>commands;
    /**
     * Változó tároló.
     */
    VariableStorage variable=VariableStorage.getInstance();
    /**
     * Az a név amivel a cikluson belül eltudjuk érni, hogy épp hanyadik futtatás van folyamatban.
     */
    String variableName;
    /**
     * Megtalálja a legközelebbi '{' karaktert. 
     * @param tokens	A lista amiben a keresés történik.
     * @return 		A listában lévõ indexét, ha talál, ha nem akkor -1-et.
     */
    private int findCurly(List<Token> tokens){
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType()==TokenTypes.CURLYLP){
                return i;
            }
        }
        return -1;
    }
    /**
     * Az osztály konstruktora. A bejövõ tokeneket ketté választja, és azokat értelmezi.
     * @param tokens A ciklushoz tartozó tokenek.
     * @throws InputErrorException
     */
    public LoopExpression(List<Token> tokens) throws InputErrorException {
        ShuntingYardParser shuni=new ShuntingYardParser();
        number=shuni.convertTokenToAST(tokens.subList(0,findCurly(tokens)));
        Parser p=new Parser();
        commands=p.parse(tokens.subList(findCurly(tokens)+1,tokens.size()-1));
    }
    /**Hozzáadja a változók közé a megfelelõ névvel, a ciklusváltozót.
     * Lefuttatja a ciklus belsejében lévõ utasításokat és közben frissíti a ciklusváltozót.
     * A legvégén kitörli a változót.
     * 
     * @param p		A játékban lévõ Poloska objektum.
     * @throws InputErrorException	Ha negatív szám kerülne a lefutás számának helyére.
     */
    @Override
    public void execute(Poloska p) throws InputErrorException{
        variableName=variable.giveLoopName();
        variable.addVariable(variableName,0.0,true);
        double num=number.evaluate();
        if(num<0){
            throw new InputErrorException("Negativ number in loop");
        }
        for (int i = 0; i < num; i++) {
            variable.updateVariable(variableName,(double)i);
            for(Command c: commands){
                c.execute(p);
            }
            variable.list();
        }
        variable.removeVariable(variableName,true);
    }
}
