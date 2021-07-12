package Commands;

import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import Nodes.ASTNode;
import Poloska.Poloska;
import Exception.InputErrorException;
import java.util.List;

public class PenWidthExpression implements Command {
	/**A kapott vastags�g */
    private ASTNode number;
    /**Az oszt�ly konstruktora. Ki�rt�keli a kapott param�tert sz�mk�nt.
     * 
     * @param tokens A bej�v� bemenet ami ehez a kifejez�shez tartozik.
     * @throws InputErrorException
     */
    public PenWidthExpression(List<Token> tokens ) throws InputErrorException{
        ShuntingYardParser numParser= new ShuntingYardParser();
        number=numParser.convertTokenToAST(tokens);
    }
    /**
     * Be�ll�tja a toll vastags�g�t.
     */
    @Override
    public void execute(Poloska p) {
        p.getPen().setWidth(number.evaluate());
    }
}
