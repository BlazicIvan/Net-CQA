package Graph.Node;

import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.CtVariable;

public class FieldNode extends GraphNode {

    private final CtVariable ctVariable;

    @Override
    protected CtModifiable getCtModifiable() {
        return ctVariable;
    }

    @Override
    public String getTypeName() {
        return "field";
    }

    public FieldNode(CtVariable ctVariable) {
        this.ctVariable = ctVariable;
    }

    @Override
    public String getElementName() {
        return ctVariable.getSimpleName();
    }
}
