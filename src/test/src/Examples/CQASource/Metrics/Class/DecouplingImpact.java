package Metrics.Class;

import Graph.Node.ClassNode;
import Graph.Structural.TypeDependencyCodeGraph;

public class DecouplingImpact extends ClassMetric {

    public DecouplingImpact(TypeDependencyCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    @Override
    protected double calculate() {
        return graph.getNumberOfPathsThroughNode(classNode);
    }

    @Override
    public String getId() {
        return "DI";
    }

}
