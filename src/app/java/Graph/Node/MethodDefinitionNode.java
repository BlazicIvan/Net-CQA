package Graph.Node;

import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtModifiable;

public class MethodDefinitionNode extends GraphNode {

    private final CtMethod ctMethod;

    @Override
    protected CtModifiable getCtModifiable() {
        return ctMethod;
    }

    @Override
    public String getTypeName() {
        return "method";
    }

    public MethodDefinitionNode(CtMethod ctMethod) {
        this.ctMethod = ctMethod;
    }

    public CtMethod getCtMethod() {
        return ctMethod;
    }

    @Override
    public String getElementName() {
        return ctMethod.getSignature();
    }
}
