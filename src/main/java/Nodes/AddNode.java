package Nodes;

public class AddNode extends ASTNode {
    public AddNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Kiértékeli a jobb és bal oldali szomszédot és összeadja õket.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()+rightNode.evaluate();
    }
}
