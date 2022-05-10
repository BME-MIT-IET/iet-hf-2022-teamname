package Commands;
import InputTranslator.Token;
import InputTranslator.ShuntingYardParser;
import Nodes.ASTNode;
import Poloska.Poloska;
import Exception.InputErrorException;
import java.util.List;

public class MoveExpression implements Command{
	/**
	 * A szám ahány egységet haladunk elõre.
	 */
    private ASTNode number;
    /**
     * Az irány amerre haladunk, ha -1 akkor hátra ha 1 akkor meg elõre.
     */
    private int dir;
    public MoveExpression(List<Token> tokens, int dir) throws InputErrorException{
        ShuntingYardParser numParser= new ShuntingYardParser();
        number=numParser.convertTokenToAST(tokens);
        this.dir=dir;
    }
    @Override
    public void execute(Poloska p) {
            p.move(dir*number.evaluate());
    }
}
