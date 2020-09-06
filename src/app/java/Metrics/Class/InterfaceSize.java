package Metrics.Class;

import Graph.CodeGraph;
import Graph.Node.*;
import Graph.Structural.StructuralCodeGraph;
import spoon.reflect.declaration.ModifierKind;

import java.util.HashSet;
import java.util.Set;

public class InterfaceSize extends ClassMetric {

    public InterfaceSize(StructuralCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
        interfaceElements = new HashSet<>();
    }
    private Set<GraphNode> interfaceElements;

    @Override
    protected double calculate() {
        interfaceElements = graph.getNeighboursByFilter(CodeGraph.EdgeDirection.OUT, classNode, new NodeFilter() {
            @Override
            public boolean acceptNode(GraphNode node) {
                return ModifierKind.PUBLIC.equals(node.getAccessModifier()) &&
                        ((node instanceof FieldNode)
                                || (node instanceof MethodDefinitionNode)
                                || (node instanceof EnumNode));
            }
        });

        addExtraInfoNodeList("Interface elements", interfaceElements);

        return interfaceElements.size();
    }

    @Override
    public String getId() {
        return "IS";
    }

}
