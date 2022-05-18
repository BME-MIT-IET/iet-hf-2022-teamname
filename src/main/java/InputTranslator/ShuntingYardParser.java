package InputTranslator;

import Nodes.*;

import java.util.*;
import Exception.InputErrorException;
class Operator {
	/**Az adott oper�tor jobb vagy bal oldali asszociativit�s�.*/
    private boolean rightAssociative;
    /**Ki�rt�kel�si sorrend ez alapj�n van eld�ntve. */
    private int precedence;
    /**Az adott szimb�lum ami reprezent�lja.*/
    private char symbol;
    Operator(char symbol, boolean rightAssociative, int precedence){
        this.symbol=symbol;
        this.precedence=precedence;
        this.rightAssociative=rightAssociative;
    }
    /**�sszehasonl�t 2 oper�tort a ki�rt�kel�si priorit�suk alapj�n.
     * 
     * @param other A m�sik oper�tor.
     * @return	Ha egyenl�ek akkor 0, ha az other precedenci�ja kissebb akkor 0-n�l nagyobb, ha nagyobb akkor meg 0-n�l nagyobb sz�mot ad.
     */
    public int comparePrecedence(Operator other){
        return Integer.compare(precedence, other.precedence);
    }
    public boolean isRightAssociative(){
        return rightAssociative;
    }
    public char getSymbol(){
        return symbol;
    }

}

public class ShuntingYardParser {
	/**
	 * Azon oper�torok amiket az alhoritmus felismer.
	 */
    private Map<Character, Operator> operators;
    /**
     * Felt�lti az oper�tor t�bl�t a megfelel� elemekkkel.
     */
    public ShuntingYardParser(){
        this.operators=new HashMap<>();
        operators.put('+',new Operator('+',false,2));
        operators.put('-',new Operator('-',false,2));
        operators.put('*',new Operator('*',false,3));
        operators.put('/',new Operator('+',false,3));
        operators.put('^',new Operator('+',true,4));
    }
    /**Az operand stack tetej�re r�tol egy �j m�velet Nodeot, melynek be�ll�tja a jobb �s bal oldali szomsz�d Node-j�t
     * az operadn stack 1. �s 2 elem�re.
     * 
     * @param stack 	Az operandStack
     * @param operator	Az adott m�veletet beazonost� karakter.
     */
    private void addNode(Deque<ASTNode> stack, char operator) {
         ASTNode rightASTNode = stack.pop();
         ASTNode leftASTNode = stack.pop();
        switch(operator){
            case'+':
                stack.push(new AddNode(String.valueOf(operator), leftASTNode, rightASTNode));
                break;
            case'-':
                stack.push(new SubstractNode(String.valueOf(operator), leftASTNode, rightASTNode));
                break;
            case'*':
                stack.push(new MultiplyNode(String.valueOf(operator), leftASTNode, rightASTNode));
                break;
            case'/':
                stack.push(new DivisionNode(String.valueOf(operator), leftASTNode, rightASTNode));
                break;
            case'^':
                stack.push(new PowerNode(String.valueOf(operator), leftASTNode, rightASTNode));
                break;
            default:
            	break;
        }

    }
    /**A bej�v� tokeneket �talak�tja egy AST-re a Shunting Yard Algoritmus alapj�n.
     * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
     * A tokeneket egyes�vel olvassa be, �s a t�pusa alapj�n d�nti el a megfelel� m�veletet.
     * Az algoritmus f� m�k�d�si alapja 2 stack: az operandStack, �s az operatorStack.
     * Az operandStackre ker�lnek maga az elk�sz�tett AST Nodeok. Az operatorStack pedig ideiglenesen t�rolja a be�rkez� m�veleteket.
     * 
     * Ha sz�mot, @(random sz�m), vagy egy $v�ltoz�t kap akkor azt r�gt�n belerakja az operandStackbe null szomsz�dokkal.
     * Ha operatort tal�l, akkor megn�zi, hogy az operatorStack tetej�n milyen oper�tor van �s am�g annak nagyobb a precedenci�ja(vagy ugyanakkora �s token bal asszociativit�s�) 
     * addig onnan veszi a m�veleteket �s azokat adja, hozz� az operandStackre AddNode()-al. Azt�n a tokent r�rakja az operator stackre.
     * Ha (-t tal�l akkor akkor azt szimpl�n r�tolja az operatorStackre.
     * Ha )-t tal�l akkor eg�szen addig am�g nem tal�l (-t addig az �sszes oper�tort az operandStackr�l r�tolja a kimenetre.
     * HA elfogyott a beolvasni val� tokenek, de m�g maradt az operatorStackbe m�velet akkor azokat is hozz�adja a kimenetre addNode()-al.
     * 
     * @param tokens A bej�v� tokenek, ami a m�veleteket tartalmazza.
     * @return	A kre�lt AST gy�k�r nodeja.
     * @throws InputErrorException Ha a bej�v� tokenekb�l nem �rtelmezhet� sz�mk�nt.
     */
    public ASTNode convertTokenToAST(List<Token> tokens) throws InputErrorException {
        Deque<Character> operatorStack = new ArrayDeque<>();
        Deque<ASTNode> operandStack =new ArrayDeque<>();

        main:
            for(Token t: tokens){
                switch(t.getType()){
                    case LPARAM:
                        operatorStack.push('(');
                        break;
                    case RPARAM:
                        while(!operatorStack.isEmpty()){
                            char popped = operatorStack.pop();
                            if(popped=='('){
                                continue main;
                            }else{
                                addNode(operandStack,popped);
                            }
                        }

                    case OPERATOR:
                        Operator o1 =operators.get(t.getValue().charAt(0));
                        Operator o2;
                        while(!operatorStack.isEmpty() && null !=(o2 = operators.get(operatorStack.peek()))){
                            if((!o1.isRightAssociative() && 0 == o1.comparePrecedence(o2)) || o1.comparePrecedence(o2) < 0){
                                operatorStack.pop();
                                addNode(operandStack,o2.getSymbol());
                            }
                            else{
                                break;
                            }
                        }
                        operatorStack.push(t.getValue().charAt(0));
                        break;
                    case NUMBER:
                        operandStack.push(new NumberNode(t.getValue(),null,null));
                        break;
                    case VARIABLE:
                        operandStack.push(new VariableNode(t.getValue(),null,null));
                        break;
                    case RANDOM:
                        operandStack.push(new RandomNode(t.getValue(),null,null));
                        break;
                    default:
                        throw new InputErrorException(t.getValue()+" is not a number!");
                }

            }
        while(!operatorStack.isEmpty()) {
            addNode(operandStack, operatorStack.pop());
        }

        if(operandStack.isEmpty()){
            throw new InputErrorException("Number missing!");
        }
        return operandStack.pop();
    }
}
