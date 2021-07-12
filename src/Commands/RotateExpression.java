package Commands;
import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import Nodes.ASTNode;
import Poloska.Poloska;
import Exception.InputErrorException;
import java.util.List;

public class RotateExpression implements Command{
	/** A forgás értéke szögekben*/
    private ASTNode number;
    /**
     * Az irány értéke, ha -1 akkor jobbra, ha 1 akkor meg balra fordul.
     */
    private int dir;
    /**Az osztály konstruktora. Kiértékeli a kapott paramétereket.
     * 
     * @param tokens Bejövõ tokenek amik ehez a kifejezéshez tartoznak.
     * @param dir	Az irány ami vagy 1 vagy -1 lehet.
     * @throws InputErrorException
     */
    public RotateExpression(List<Token> tokens, int dir) throws InputErrorException {
        ShuntingYardParser numParser= new ShuntingYardParser();
        number=numParser.convertTokenToAST(tokens);
        this.dir=dir;
    }
    /**
     * A megadott menyiséget fordul az iránynak megfelelõen.
     */
    @Override
    public void execute(Poloska p) {
        p.rotate(dir* number.evaluate());
    }
}
