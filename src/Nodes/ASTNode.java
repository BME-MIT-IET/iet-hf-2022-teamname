package Nodes;

import java.util.regex.Pattern;

public abstract class ASTNode {
	/**
	 * Sz�veges form�ban a Node azonos�t� �r�ke.
	 */
   protected String value;
   /**bal szomsz�d */
    protected ASTNode leftNode;
    /**jobb szomsz�d */
    protected ASTNode rightNode;
    /**Node val�s �rt�k�nek visszaad�sa */
    public abstract double evaluate();
    public ASTNode(String value, ASTNode left, ASTNode right){
        leftNode=left;
        rightNode=right;
        this.value=value;
    }
    @Override
    public boolean equals(Object o) {
    	Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    	ASTNode other=(ASTNode)o;
    	if(other.leftNode==null) {
    		return other.value.equals(value);
    	}
    	return leftNode.equals(other.leftNode)&& rightNode.equals(other.rightNode) && value.equals(other.value);
    }
}
