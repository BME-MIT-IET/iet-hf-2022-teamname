package Nodes;
import variables.VariableStorage;
public class VariableNode extends ASTNode{
    public VariableNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Kikeresi a value alapján a megfelelõ változót a változó tárolóból és visszadja számként.
     */
    @Override
    public double evaluate(){
        VariableStorage vars=VariableStorage.getInstance();
        return vars.getVariable(value);
    }
}
