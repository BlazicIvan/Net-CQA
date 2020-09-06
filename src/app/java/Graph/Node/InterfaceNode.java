package Graph.Node;

import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtModifiable;

public class InterfaceNode extends GraphNode {

    private final CtInterface ctInterface;

    @Override
    protected CtModifiable getCtModifiable() {
        return ctInterface;
    }

    @Override
    public String getTypeName() {
        return "interface";
    }

    public InterfaceNode(CtInterface ctInterface) {
        this.ctInterface = ctInterface;
    }

    @Override
    public String getElementName() {
        return ctInterface.getQualifiedName();
    }
}
