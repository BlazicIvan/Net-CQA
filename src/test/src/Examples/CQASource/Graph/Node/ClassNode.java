package Graph.Node;

import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.CtType;

public class ClassNode extends GraphNode {

    private final CtType ctType;

    @Override
    protected CtModifiable getCtModifiable() {
        return ctType;
    }

    @Override
    public String getTypeName() {
        return "class";
    }

    public ClassNode(CtType ctType) {
        this.ctType = ctType;
    }

    @Override
    public String getElementName() {
        return ctType.getQualifiedName();
    }

    public String getQualifiedName() {
        return ctType.getQualifiedName();
    }
}
