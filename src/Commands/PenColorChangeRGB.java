package Commands;

import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import InputTranslator.TokenTypes;
import Nodes.ASTNode;
import Poloska.Poloska;
import javafx.scene.paint.Color;
import Exception.InputErrorException;
import java.util.ArrayList;
import java.util.List;

public class PenColorChangeRGB implements Command{
	/**A piros sz�n �rt�ke */
    ASTNode red;
    /**A z�ld sz�n �rt�ke */
    ASTNode green;
    /**A k�k sz�n �rt�ke */
    ASTNode blue;
    /**
	 * A bemeneten megadott Token list�t a megadott Token t�pus ment�n sz�tv�lasztja.
	 * 
	 * @param tokens	A sz�mokat tartalmaz� Token sorozat.
	 * @param tt		Az adott token t�pus ami ment�n az elv�laszt�st el kell v�gezni.
	 * @return 			A tokenek sz�tv�lasztva a megadott elv�laszt� ment�n. List�k t�mbje.
	 */
    private ArrayList<Token>[] split(List<Token> tokens,TokenTypes tt){
        int k=0;
       ArrayList<Token>[] sep=new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            sep[i] = new ArrayList<Token>();
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
    /**Az oszt�ly konstruktora. A bemenetet felosztja �s a kapott sz�mokat ki�rt�keli.
     * 
     * @param tokens
     * @throws InputErrorException
     */
    public PenColorChangeRGB(List<Token> tokens) throws InputErrorException{
        ShuntingYardParser shuni=new ShuntingYardParser();
        ArrayList<Token>[] separated=split(tokens,TokenTypes.COMMA);
        red= shuni.convertTokenToAST(separated[0]);
        green= shuni.convertTokenToAST(separated[1]);
        blue= shuni.convertTokenToAST(separated[2]);
    }
    /**
     * Be�ll�tja a toll sz�n�t a kapott piros, z�ld, k�k sz�n�rt�kek alapj�n.
     */
    @Override
    public void execute(Poloska p) {
        Color col= Color.rgb((int)red.evaluate(),(int)green.evaluate(),(int)blue.evaluate());
        p.getPen().setColor(col);
    }
}
