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
	/**A piros szín értéke */
    ASTNode red;
    /**A zöld szín értéke */
    ASTNode green;
    /**A kék szín értéke */
    ASTNode blue;
    /**
	 * A bemeneten megadott Token listát a megadott Token típus mentén szétválasztja.
	 * 
	 * @param tokens	A számokat tartalmazó Token sorozat.
	 * @param tt		Az adott token típus ami mentén az elválasztást el kell végezni.
	 * @return 			A tokenek szétválasztva a megadott elválasztó mentén. Listák tömbje.
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
    /**Az osztály konstruktora. A bemenetet felosztja és a kapott számokat kiértékeli.
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
     * Beállítja a toll színét a kapott piros, zöld, kék színértékek alapján.
     */
    @Override
    public void execute(Poloska p) {
        Color col= Color.rgb((int)red.evaluate(),(int)green.evaluate(),(int)blue.evaluate());
        p.getPen().setColor(col);
    }
}
