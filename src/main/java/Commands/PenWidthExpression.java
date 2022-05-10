package Commands;

import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import Nodes.ASTNode;
import Poloska.Poloska;
import Exception.InputErrorException;
import java.util.List;

public class PenWidthExpression implements Command {
	/**A kapott vastagság */
    private ASTNode number;
    /**Az osztály konstruktora. Kiértékeli a kapott paramétert számként.
     * 
     * @param tokens A bejövõ bemenet ami ehez a kifejezéshez tartozik.
     * @throws InputErrorException
     */
    public PenWidthExpression(List<Token> tokens ) throws InputErrorException{
        ShuntingYardParser numParser= new ShuntingYardParser();
        number=numParser.convertTokenToAST(tokens);
    }
    /**
     * Beállítja a toll vastagságát.
     */
    @Override
    public void execute(Poloska p) {
        p.getPen().setWidth(number.evaluate());
    }
}
