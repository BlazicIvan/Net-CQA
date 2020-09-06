package Metrics.Class;

import Graph.CodeGraph;
import Graph.Edge.GraphEdge;
import Graph.Node.ClassNode;
import Graph.Node.MethodDefinitionNode;
import Graph.Structural.ClassCodeGraph;

import java.util.Set;

public class WeightedMethodCount extends ClassMetric {

    public WeightedMethodCount(ClassCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    @Override
    protected double calculate() {
        double wmc = 0;

        Set<GraphEdge> edges = graph.getEdgesByFilter(CodeGraph.EdgeDirection.OUT,
                classNode,
                node -> (node instanceof  MethodDefinitionNode));

        for(GraphEdge edge : edges) {
            wmc += edge.getWeight();
            addExtraInfoString("Complexity of method "+edge.getTarget().getElementName(),
                    Double.toString(edge.getWeight()));
        }

        return wmc;
    }

    @Override
    public String getId() {
        return "WMC";
    }

}
