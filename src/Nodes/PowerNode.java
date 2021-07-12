package Nodes;

public class PowerNode extends ASTNode{
    public PowerNode(String s, ASTNode left, ASTNode right) {
        super(s, left, right);
    }
    /**
     * Ki�rt�keli a 2 szomsz�dot, �s a hatv�nyoz�st elv�gzi.A bal oldali lezs a hatv�nyalap a jobb pedig a hatv�nykitev�.
     */
    @Override
    public double evaluate() {
        return Math.pow(leftNode.evaluate(), rightNode.evaluate());
    }
}