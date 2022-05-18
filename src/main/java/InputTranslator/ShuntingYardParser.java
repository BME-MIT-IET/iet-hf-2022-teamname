package InputTranslator;

import Nodes.*;

import java.util.*;
import Exception.InputErrorException;
class Operator {
	/**Az adott operátor jobb vagy bal oldali asszociativitású.*/
    private boolean rightAssociative;
    /**Kiértékelési sorrend ez alapján van eldöntve. */
    private int precedence;
    /**Az adott szimbólum ami reprezentálja.*/
    private char symbol;
    Operator(char symbol, boolean rightAssociative, int precedence){
        this.symbol=symbol;
        this.precedence=precedence;
        this.rightAssociative=rightAssociative;
    }
    /**Összehasonlít 2 operátort a kiértékelési prioritásuk alapján.
     * 
     * @param other A másik operátor.
     * @return	Ha egyenlõek akkor 0, ha az other precedenciája kissebb akkor 0-nál nagyobb, ha nagyobb akkor meg 0-nál nagyobb számot ad.
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
	 * Azon operátorok amiket az alhoritmus felismer.
	 */
    private Map<Character, Operator> operators;
    /**
     * Feltölti az operátor táblát a megfelelõ elemekkkel.
     */
    public ShuntingYardParser(){
        this.operators=new HashMap<>();
        operators.put('+',new Operator('+',false,2));
        operators.put('-',new Operator('-',false,2));
        operators.put('*',new Operator('*',false,3));
        operators.put('/',new Operator('+',false,3));
        operators.put('^',new Operator('+',true,4));
    }
    /**Az operand stack tetejére rátol egy új mûvelet Nodeot, melynek beállítja a jobb és bal oldali szomszéd Node-ját
     * az operadn stack 1. és 2 elemére.
     * 
     * @param stack 	Az operandStack
     * @param operator	Az adott mûveletet beazonostó karakter.
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
    /**A bejövõ tokeneket átalakítja egy AST-re a Shunting Yard Algoritmus alapján.
     * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
     * A tokeneket egyesével olvassa be, és a típusa alapján dönti el a megfelelõ mûveletet.
     * Az algoritmus fõ mûködési alapja 2 stack: az operandStack, és az operatorStack.
     * Az operandStackre kerülnek maga az elkészített AST Nodeok. Az operatorStack pedig ideiglenesen tárolja a beérkezõ mûveleteket.
     * 
     * Ha számot, @(random szám), vagy egy $változót kap akkor azt rögtön belerakja az operandStackbe null szomszédokkal.
     * Ha operatort talál, akkor megnézi, hogy az operatorStack tetején milyen operátor van és amíg annak nagyobb a precedenciája(vagy ugyanakkora és token bal asszociativitású) 
     * addig onnan veszi a mûveleteket és azokat adja, hozzá az operandStackre AddNode()-al. Aztán a tokent rárakja az operator stackre.
     * Ha (-t talál akkor akkor azt szimplán rátolja az operatorStackre.
     * Ha )-t talál akkor egészen addig amíg nem talál (-t addig az összes operátort az operandStackrõl rátolja a kimenetre.
     * HA elfogyott a beolvasni való tokenek, de még maradt az operatorStackbe mûvelet akkor azokat is hozzáadja a kimenetre addNode()-al.
     * 
     * @param tokens A bejövõ tokenek, ami a mûveleteket tartalmazza.
     * @return	A kreált AST gyökér nodeja.
     * @throws InputErrorException Ha a bejövõ tokenekbõl nem értelmezhetõ számként.
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
