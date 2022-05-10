package Nodes;
import variables.VariableStorage;
public class VariableNode extends ASTNode{
    public VariableNode(String s,ASTNode left,ASTNode right){
        super(s,left,right);
    }
    /**
     * Kikeresi a value alapj�n a megfelel� v�ltoz�t a v�ltoz� t�rol�b�l �s visszadja sz�mk�nt.
     */
    @Override
    public double evaluate(){
        VariableStorage vars=VariableStorage.getInstance();
        return vars.getVariable(value);
    }
}
