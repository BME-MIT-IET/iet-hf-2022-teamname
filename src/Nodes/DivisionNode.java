package Nodes;

public class DivisionNode extends ASTNode{
    public DivisionNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Kiértékeli a 2 szomszédot, és a balt elosztja a jobbal.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()/rightNode.evaluate();
    }
}
