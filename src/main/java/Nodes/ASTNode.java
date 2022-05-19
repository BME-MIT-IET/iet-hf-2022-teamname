package Nodes;

import java.util.regex.Pattern;

public abstract class ASTNode {
	/**
	 * Szöveges formában a Node azonosító éréke.
	 */
   protected String value;
   /**bal szomszéd */
    protected ASTNode leftNode;
    /**jobb szomszéd */
    protected ASTNode rightNode;
    /**Node valós értékének visszaadása */
    public abstract double evaluate();
    protected ASTNode(String value, ASTNode left, ASTNode right){
        leftNode=left;
        rightNode=right;
        this.value=value;
    }
    @Override
    public boolean equals(Object o) {
   // 	Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    	
    	if (o == null)
    	    return false;

    	  if (this.getClass() != o.getClass())
    	    return false;
    	
    	try {
	    	ASTNode other=(ASTNode)o;
	    	if(other.leftNode==null) {
	    		return other.value.equals(value);
	    	}
	    	return leftNode.equals(other.leftNode)&& rightNode.equals(other.rightNode) && value.equals(other.value);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    @Override
    public int hashCode() {
    	return 69;
    }
}
