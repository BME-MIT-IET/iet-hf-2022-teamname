package Nodes;

public class MultiplyNode extends ASTNode {
    public MultiplyNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Kiértékeli a 2 szomszédot, és a 2õt összeszorozza.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()* rightNode.evaluate();
    }
}
