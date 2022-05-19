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
	 * A ciklussz�mot reprezent�l� AST.
	 */
    private ASTNode number;
    /**
     * A ciklus belsej�ben l�v� utas�t�sok.
     */
    private ArrayList<Command>commands;
    /**
     * V�ltoz� t�rol�.
     */
    VariableStorage variable=VariableStorage.getInstance();
    /**
     * Az a n�v amivel a cikluson bel�l eltudjuk �rni, hogy �pp hanyadik futtat�s van folyamatban.
     */
    String variableName;
    /**
     * Megtal�lja a legk�zelebbi '{' karaktert. 
     * @param tokens	A lista amiben a keres�s t�rt�nik.
     * @return 		A list�ban l�v� index�t, ha tal�l, ha nem akkor -1-et.
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
     * Az oszt�ly konstruktora. A bej�v� tokeneket kett� v�lasztja, �s azokat �rtelmezi.
     * @param tokens A ciklushoz tartoz� tokenek.
     * @throws InputErrorException
     */
    public LoopExpression(List<Token> tokens) throws InputErrorException {
        ShuntingYardParser shuni=new ShuntingYardParser();
        number=shuni.convertTokenToAST(tokens.subList(0,findCurly(tokens)));
        Parser p=new Parser();
        commands=p.parse(tokens.subList(findCurly(tokens)+1,tokens.size()-1));
    }
    /**Hozz�adja a v�ltoz�k k�z� a megfelel� n�vvel, a ciklusv�ltoz�t.
     * Lefuttatja a ciklus belsej�ben l�v� utas�t�sokat �s k�zben friss�ti a ciklusv�ltoz�t.
     * A legv�g�n kit�rli a v�ltoz�t.
     * 
     * @param p		A j�t�kban l�v� Poloska objektum.
     * @throws InputErrorException	Ha negat�v sz�m ker�lne a lefut�s sz�m�nak hely�re.
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
