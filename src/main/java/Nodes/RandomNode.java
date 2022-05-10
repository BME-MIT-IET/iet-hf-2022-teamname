package Nodes;

public class RandomNode extends ASTNode{ ;
    public RandomNode(String s, ASTNode left, ASTNode right) {
        super(s, left, right);
    }
    /**
     * Egy random számot generál 0 és 1 között.
     */
    @Override
    public double evaluate() {
        return Math.random();
    }
}
