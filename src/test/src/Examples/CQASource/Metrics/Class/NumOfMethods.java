package Metrics.Class;

import Graph.CodeGraph.EdgeDirection;
import Graph.Node.ClassNode;
import Graph.Node.GraphNode;
import Graph.Node.MethodDefinitionNode;
import Graph.Node.NodeFilter;
import Graph.Structural.ClassCodeGraph;

import java.util.Set;

public class NumOfMethods extends ClassMetric {

    public NumOfMethods(ClassCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    @Override
    protected double calculate() {
        Set<GraphNode> methods = graph.getNeighboursByFilter(EdgeDirection.OUT, classNode,
                (NodeFilter)  node -> (node instanceof MethodDefinitionNode));
        addExtraInfoNodeList("Methods", methods);

        return methods.size();
    }

    @Override
    public String getId() {
        return "NOM";
    }

}
