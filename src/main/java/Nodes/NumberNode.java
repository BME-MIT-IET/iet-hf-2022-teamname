package Nodes;

public class NumberNode extends ASTNode {
    public NumberNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * A value �rt�kb�l kisz�ri a t�nyleges sz�m �rt�ket.
     */
    @Override
    public double evaluate() {
        return Double.parseDouble(String.valueOf(value));
    }
}
