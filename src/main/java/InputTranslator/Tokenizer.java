package InputTranslator;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

import Exception.InputErrorException;
public class Tokenizer {
    private int params;
    private int cparams;
    /**
     * A megadott sztring Tokenekre bontott alakj�t t�rolja
     */
    private ArrayList<Token> tokens=new ArrayList<>();
    /**
     * A megadott sztringre k�pez karakteriter�tort, mely seg�ts�g�vel egyess�vel v�gigolvassa a sztringet.
     */
    CharacterIterator it;
    public Tokenizer(String command) throws InputErrorException{
        it=new StringCharacterIterator(command);
        split();
        params=0;
        cparams=0;
    }

    /**
     * Az iter�tor poz�ci�ja ut�n k�zvetlen sz�mot keres. (double)
     * @return num Sz�veges form�ban a megtal�lt sz�m.
     */
    private String findNum(){
        StringBuilder num=new StringBuilder();
        while(Character.isDigit(it.current())|| it.current()=='.'){
            num.append(it.current());
            it.next();
        }
        return num.toString();
    }
    /**
     * Az iter�tor poz�ci�ja ut�n k�zvetlen�l sz�t keres. Csak bet�ket tartalmazhat. 
     * @return word Sz�veges form�ban a megtal�lt sz�.
     */
    private String findWord(){
        StringBuilder word=new StringBuilder();
        while(Character.isLetter(it.current())){
            word.append(it.current());
            it.next();
        }
        return word.toString();
    }
    /**
     * Az iter�tor poz�ci�ja ut�n k�zvetlen�l sz�t keres. Tartalmazhat bet�ket, sz�mokat �s '_' karaktert.
     * @return word Sz�veges form�ban a megtal�lt sz�.
     */
    private String findVariableName(){
        StringBuilder word=new StringBuilder();
        while(Character.isLetter(it.current())||Character.isDigit(it.current())|| it.current()=='_'){
            word.append(it.current());
            it.next();
        }
        return word.toString();
    }

    /**
     * El�ri a sztringb�l kisz�rt tokeneket.
     * @return A sztring tokenekre lebontott alakja.
     */
    public List<Token> getTokens(){
        return tokens;
    }

    /**
     * V�gig iter�l a sztring karakterein �s a megfelel� Tokeneket a list�ba helyezi.
     * Ellen�rzi, hogy a {} �s () mindenhol p�rosan szerepelnek.
     * @throws InputErrorException Amennyiben ismeretlen karakter ker�l a sztringbe, vagy valamelyik () vagy {} nem megfelel�en van lez�rva.
     */
    private void split()throws InputErrorException {
        while(it.current()!= CharacterIterator.DONE){
            if(" \n\t".indexOf(it.current())!=-1){it.next();}
            else if(Character.isDigit(it.current())){
                tokens.add(new Token(TokenTypes.NUMBER,findNum()));
            }
            else if(Character.isAlphabetic(it.current())){
                tokens.add(new Token(TokenTypes.STRING,findWord()));
            }
            else if("+*-/^".indexOf(it.current())!=-1){
                tokens.add(new Token(TokenTypes.OPERATOR,String.valueOf(it.current())));
                it.next();

            }
            else if(it.current()=='('){ tokens.add(new Token(TokenTypes.LPARAM,"")); it.next(); params++;}
            else if(it.current()==')'){ tokens.add(new Token(TokenTypes.RPARAM,"")); it.next(); params--;}
            else if(it.current()=='{'){ tokens.add(new Token(TokenTypes.CURLYLP,"")); it.next();cparams++;}
            else if(it.current()=='}'){ tokens.add(new Token(TokenTypes.CURLYRP,"")); it.next(); cparams--;}
            else if(it.current()==','){ tokens.add(new Token(TokenTypes.COMMA,"")); it.next();}
            else if(it.current()=='@'){ tokens.add(new Token(TokenTypes.RANDOM,"")); it.next();}
            else if(it.current()=='#'){
                it.next();
                tokens.add(new Token(TokenTypes.HEX,findVariableName()));
            }
            else if(it.current()=='$'){
                it.next();
                tokens.add(new Token(TokenTypes.VARIABLE,findVariableName()));
            }
            else{
                throw new InputErrorException("Invalid character: "+it.current()+" at: "+it.getIndex());
            }
        }
        if(params!=0){
            throw new InputErrorException("Incomplete ()");
        }
        if(cparams!=0){
            throw new InputErrorException("Incomplete {}");
        }
    };
}
