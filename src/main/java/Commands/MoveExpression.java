package Commands;
import InputTranslator.Token;
import InputTranslator.ShuntingYardParser;
import Nodes.ASTNode;
import Poloska.Poloska;
import Exception.InputErrorException;
import java.util.List;

public class MoveExpression implements Command{
	/**
	 * A sz�m ah�ny egys�get haladunk el�re.
	 */
    private ASTNode number;
    /**
     * Az ir�ny amerre haladunk, ha -1 akkor h�tra ha 1 akkor meg el�re.
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
