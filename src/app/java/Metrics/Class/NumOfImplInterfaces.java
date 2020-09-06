package Metrics.Class;

import CodeModel.Dependencies.ClassReference.ImplementationDependency;
import CodeModel.Dependencies.DependencyVisitor;
import Graph.CodeGraph.EdgeDirection;
import Graph.Edge.EdgeFilter;
import Graph.Node.ClassNode;
import Graph.Node.GraphNode;
import Graph.Structural.TypeDependencyCodeGraph;

import java.util.Set;

public class NumOfImplInterfaces extends ClassMetric {

    public NumOfImplInterfaces(TypeDependencyCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    @Override
    protected double calculate() {
        Set<GraphNode> implInterfaces = graph.getNeighboursByFilter(EdgeDirection.OUT, classNode,
                (EdgeFilter) edge -> edge.hasDependencies(new DependencyVisitor() {
            @Override
            public boolean visitImplementationDependency(ImplementationDependency dependency) {
                return true;
            }
        }));

        addExtraInfoNodeList("Implemented interfaces", implInterfaces);
        return implInterfaces.size();
    }
    @Override
    public String getId() {
        return "NII";
    }

}
