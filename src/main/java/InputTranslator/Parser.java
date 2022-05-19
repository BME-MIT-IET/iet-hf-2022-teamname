package InputTranslator;

import Commands.*;

import java.util.ArrayList;
import java.util.List;
import Exception.InputErrorException;
import variables.FunctionStorage;

public class Parser {
	/**Egy �j parancsot hoz l�tre a param�terek alapj�n, ha nem tal�lja a be�p�tettek k�z�tt akkor az
	 * elj�r�sok k�z�tt keresi. Ez azt eredm�nyezi, hogy nem tudjuk fel�l�rni az alap�rtelmezett parancsokat.
	 * 
	 * @param id		Ez alapj�n azonos�tja be a met�dus milyen parancsot kell l�trehoznia.
	 * @param tokens	A l�trehzott parancs ezekb�l a tokenekb�l fogja kisz�rni a sz�ks�ges param�tereit.
	 * @return			Az �j parancs
	 * @throws InputErrorException	Amennyiben nem tal�lhat� az adott id-vel semmilyen parancs
	 */
    private Command createExpression(String id, List<Token> tokens) throws InputErrorException{
        switch(id.toLowerCase()){
            case"e":
                return new MoveExpression(tokens,1);
            case"j":
                return new RotateExpression(tokens,-1);
            case"b":
                return new RotateExpression(tokens,1);
            case"h":
                return new MoveExpression(tokens,-1);
            case"loop":
                return new LoopExpression(tokens);
            case "pw":
                return new PenWidthExpression(tokens);
            case "prgb":
                return new PenColorChangeRGB(tokens);
            case "phex":
                return new PenColorChangeHEX(tokens);
            case "clr":
                return new ClearScreen();
            case "pu":
                return new PenUp();
            case "pd":
                return new PenDown();
            default:
                if(FunctionStorage.getInstance().hasFunction(id)){
                    return new FunctionExpression(id,tokens);
                }
                throw new InputErrorException("No such command: "+id);
        }

    }
    /**A bej�v� token sorozatot �talak�tja futtathat� parancsokk�. A tokeneket egyess�vel olvassa be, �s 
     * amikor STRING t�pus� tokent kap akkor tudja, hogy az egy �j parancs, de nem hozza r�gt�n l�tre mivel
     * m�g nem olvasta be az ahoz sz�ks�ges param�ter tokeneket. Am�g az olvasott token nem STRING addig a tokenBuffer-be
     * olvassa be a tokeneket eg�szen addig am�g nem tal�l �jabb STRING-et vagyis parancsot.
     * Ekkor tudja, hogy az el�z� kifejez�shez az �sszes sz�ks�ges param�tert beolvastuk. 
     * Ezut�n l�trehozza az �j parancsot az el�z� parancshoz param�terk�nt felhaszn�lva a tokenBuffert.
     * Ugyanez t�rt�nik ha a tokenek v�g�re �r�nk.
     * 
     * A ciklusokban l�v� tokeneket nem szeretn�nk m�g itt ki�rt�kelni, ez�rt sz�montartjuk, h�ny { illetve } karakterrel tal�lkoztunk.
     * Ha nem egyenl� az �rt�k�k akkor az azt jelenti, hogy �ppen egy ciklus belsej�t olvassuk �s hi�ba olvasunk STRING-et nem akarjuk, hogy �j parancs j�jj�n l�tre.
     * 
     * @param tokens	A bej�v� utas�t�sok token form�ja.
     * @return			A gener�lt parancsok a futtat�si sorrendben.
     * @throws InputErrorException Ha nem �rtelmezhet�k a kapott tokenek parancsk�nt.
     */
    public ArrayList<Command> parse(List<Token> tokens) throws InputErrorException{
        ArrayList<Command> commands=new ArrayList<>();
        List<Token> tokenBuffer=new ArrayList<>();
        Token previousCommand=null;
        for(Token t :tokens){
            System.out.println(t.toString());
        }
        int leftCurly=0;
        int rightCurly=0;
        for(Token t: tokens){
            if(t.getType()==TokenTypes.STRING &&(leftCurly==rightCurly)){
               if(previousCommand!=null) {
                   commands.add(createExpression(previousCommand.getValue(), tokenBuffer));
                   tokenBuffer.clear();
               }
               previousCommand=t;
            }

            else{
                if(t.getType()==TokenTypes.CURLYLP){
                    leftCurly++;
                }
                else if(t.getType()==TokenTypes.CURLYRP){
                    rightCurly++;
                }
                tokenBuffer.add(t);
            }
        }
        if(previousCommand!=null) {
            commands.add(createExpression(previousCommand.getValue(), tokenBuffer));
            tokenBuffer.clear();
        }
        if(commands.isEmpty()){
            throw new InputErrorException("Nincs parancs!");
        }
        return commands;
    }
}
