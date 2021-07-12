package Commands;

import InputTranslator.Token;
import Poloska.Poloska;
import javafx.scene.paint.Color;

import java.util.List;


public class PenColorChangeHEX implements Command{
	/**
	 * A sz�n hex �rt�ke. Form�tum: #123123
	 */
    String hex;
    public PenColorChangeHEX(List<Token> tokens){
        hex=tokens.get(0).getValue();
    }
    /**Be�ll�tja a toll sz�n�t az adott hex �rt�kre.
     * 
     * @param p Az akt�v Poloska objektum.
     */
    @Override
    public void execute(Poloska p) {
        p.getPen().setColor(Color.web(hex));
    }
}
