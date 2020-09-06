package Graph.Node;

import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.ModifierKind;

public abstract class GraphNode {

    private static int nextId = 0;
    private final int id = nextId++;

    public String getId() {
        return ""+id;
    }

    protected abstract CtModifiable getCtModifiable();

    public abstract String getTypeName();

    public abstract String getElementName();

    public ModifierKind getAccessModifier() {
        return getCtModifiable().getVisibility();
    }

    public String getAccessModifierString() {
        String modifierString = "default";
        ModifierKind accessModifier = getAccessModifier();
        if(accessModifier != null) {
            modifierString = accessModifier.toString();
        }
        return modifierString;
    }

}
