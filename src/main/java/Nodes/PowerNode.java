package Nodes;

public class PowerNode extends ASTNode{
    public PowerNode(String s, ASTNode left, ASTNode right) {
        super(s, left, right);
    }
    /**
     * Kiértékeli a 2 szomszédot, és a hatványozást elvégzi.A bal oldali lezs a hatványalap a jobb pedig a hatványkitevõ.
     */
    @Override
    public double evaluate() {
        return Math.pow(leftNode.evaluate(), rightNode.evaluate());
    }
}