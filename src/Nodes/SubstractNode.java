package Nodes;

public class SubstractNode extends ASTNode{
    public SubstractNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Kiértékeli a jobb és bal oldali szomszédot és kivonja a balból a jobb oldalit.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()-rightNode.evaluate();
    }
}
