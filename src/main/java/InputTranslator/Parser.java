package InputTranslator;

import Commands.*;

import java.util.ArrayList;
import java.util.List;
import Exception.InputErrorException;
import variables.FunctionStorage;

public class Parser {
	/**Egy új parancsot hoz létre a paraméterek alapján, ha nem találja a beépítettek között akkor az
	 * eljárások között keresi. Ez azt eredményezi, hogy nem tudjuk felülírni az alapértelmezett parancsokat.
	 * 
	 * @param id		Ez alapján azonosítja be a metódus milyen parancsot kell létrehoznia.
	 * @param tokens	A létrehzott parancs ezekbõl a tokenekbõl fogja kiszûrni a szükséges paramétereit.
	 * @return			Az új parancs
	 * @throws InputErrorException	Amennyiben nem található az adott id-vel semmilyen parancs
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
    /**A bejõvõ token sorozatot átalakítja futtatható parancsokká. A tokeneket egyessével olvassa be, és 
     * amikor STRING típusú tokent kap akkor tudja, hogy az egy új parancs, de nem hozza rögtön létre mivel
     * még nem olvasta be az ahoz szükséges paraméter tokeneket. Amíg az olvasott token nem STRING addig a tokenBuffer-be
     * olvassa be a tokeneket egészen addig amíg nem talál újabb STRING-et vagyis parancsot.
     * Ekkor tudja, hogy az elõzõ kifejezéshez az összes szükséges paramétert beolvastuk. 
     * Ezután létrehozza az új parancsot az elõzõ parancshoz paraméterként felhasználva a tokenBuffert.
     * Ugyanez történik ha a tokenek végére érünk.
     * 
     * A ciklusokban lévõ tokeneket nem szeretnénk még itt kiértékelni, ezért számontartjuk, hány { illetve } karakterrel találkoztunk.
     * Ha nem egyenlõ az értékük akkor az azt jelenti, hogy éppen egy ciklus belsejét olvassuk és hiába olvasunk STRING-et nem akarjuk, hogy új parancs jöjjön létre.
     * 
     * @param tokens	A bejövõ utasítások token formája.
     * @return			A generált parancsok a futtatási sorrendben.
     * @throws InputErrorException Ha nem értelmezhetõk a kapott tokenek parancsként.
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
