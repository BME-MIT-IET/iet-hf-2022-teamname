package Nodes;

public class NumberNode extends ASTNode {
    public NumberNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * A value értékbõl kiszûri a tényleges szám értéket.
     */
    @Override
    public double evaluate() {
        return Double.parseDouble(String.valueOf(value));
    }
}
