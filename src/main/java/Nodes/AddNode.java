package Nodes;

public class AddNode extends ASTNode {
    public AddNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Ki�rt�keli a jobb �s bal oldali szomsz�dot �s �sszeadja �ket.
     */
    @Override
    public double evaluate() {
        return leftNode.evaluate()+rightNode.evaluate();
    }
}
