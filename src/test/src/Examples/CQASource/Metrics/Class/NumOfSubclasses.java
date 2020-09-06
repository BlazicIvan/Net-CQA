package Metrics.Class;

import CodeModel.Dependencies.ClassReference.InheritanceDependency;
import CodeModel.Dependencies.DependencyVisitor;
import Graph.CodeGraph.EdgeDirection;
import Graph.Edge.EdgeFilter;
import Graph.Node.ClassNode;
import Graph.Node.GraphNode;
import Graph.Structural.TypeDependencyCodeGraph;

import java.util.Set;

public class NumOfSubclasses extends ClassMetric {

    public NumOfSubclasses(TypeDependencyCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    private class InheritanceVisitor extends DependencyVisitor {
        @Override
        public boolean visitInheritanceDependency(InheritanceDependency dependency) {
            return true;
        }
    }

    @Override
    protected double calculate() {
        Set<GraphNode> subclasses = graph.getNeighboursByFilter(EdgeDirection.IN, classNode,
                (EdgeFilter) edge -> edge.hasDependencies(new InheritanceVisitor()));
        addExtraInfoNodeList("Subclasses", subclasses);

        return subclasses.size();
    }

    @Override
    public String getId() {
        return "NSUB";
    }

}
