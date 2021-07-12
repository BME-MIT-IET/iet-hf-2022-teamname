package Nodes;

public class RandomNode extends ASTNode{ ;
    public RandomNode(String s, ASTNode left, ASTNode right) {
        super(s, left, right);
    }
    /**
     * Egy random sz�mot gener�l 0 �s 1 k�z�tt.
     */
    @Override
    public double evaluate() {
        return Math.random();
    }
}
