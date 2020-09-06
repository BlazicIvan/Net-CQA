package Graph.Node;

import spoon.reflect.declaration.CtEnum;
import spoon.reflect.declaration.CtModifiable;

public class EnumNode extends GraphNode {

    private CtEnum ctEnum;

    public EnumNode(CtEnum ctEnum) {
        this.ctEnum = ctEnum;
    }

    @Override
    protected CtModifiable getCtModifiable() {
        return ctEnum;
    }

    @Override
    public String getTypeName() {
        return "enum";
    }

    @Override
    public String getElementName() {
        return ctEnum.getSimpleName();
    }
}
