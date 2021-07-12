package Nodes;

public class SubstractNode extends ASTNode{
    public SubstractNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Ki�rt�keli a jobb �s bal oldali szomsz�dot �s kivonja a balb�l a jobb oldalit.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()-rightNode.evaluate();
    }
}
