package Nodes;

public class DivisionNode extends ASTNode{
    public DivisionNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Ki�rt�keli a 2 szomsz�dot, �s a balt elosztja a jobbal.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()/rightNode.evaluate();
    }
}
