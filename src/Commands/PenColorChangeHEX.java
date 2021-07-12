package Commands;

import InputTranslator.Token;
import Poloska.Poloska;
import javafx.scene.paint.Color;

import java.util.List;


public class PenColorChangeHEX implements Command{
	/**
	 * A szín hex értéke. Formátum: #123123
	 */
    String hex;
    public PenColorChangeHEX(List<Token> tokens){
        hex=tokens.get(0).getValue();
    }
    /**Beállítja a toll színét az adott hex értékre.
     * 
     * @param p Az aktív Poloska objektum.
     */
    @Override
    public void execute(Poloska p) {
        p.getPen().setColor(Color.web(hex));
    }
}
