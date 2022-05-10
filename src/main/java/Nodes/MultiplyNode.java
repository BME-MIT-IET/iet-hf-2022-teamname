package Nodes;

public class MultiplyNode extends ASTNode {
    public MultiplyNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Ki�rt�keli a 2 szomsz�dot, �s a 2�t �sszeszorozza.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()* rightNode.evaluate();
    }
}
