package Commands;
import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import Nodes.ASTNode;
import Poloska.Poloska;
import Exception.InputErrorException;
import java.util.List;

public class RotateExpression implements Command{
	/** A forg�s �rt�ke sz�gekben*/
    private ASTNode number;
    /**
     * Az ir�ny �rt�ke, ha -1 akkor jobbra, ha 1 akkor meg balra fordul.
     */
    private int dir;
    /**Az oszt�ly konstruktora. Ki�rt�keli a kapott param�tereket.
     * 
     * @param tokens Bej�v� tokenek amik ehez a kifejez�shez tartoznak.
     * @param dir	Az ir�ny ami vagy 1 vagy -1 lehet.
     * @throws InputErrorException
     */
    public RotateExpression(List<Token> tokens, int dir) throws InputErrorException {
        ShuntingYardParser numParser= new ShuntingYardParser();
        number=numParser.convertTokenToAST(tokens);
        this.dir=dir;
    }
    /**
     * A megadott menyis�get fordul az ir�nynak megfelel�en.
     */
    @Override
    public void execute(Poloska p) {
        p.rotate(dir* number.evaluate());
    }
}
