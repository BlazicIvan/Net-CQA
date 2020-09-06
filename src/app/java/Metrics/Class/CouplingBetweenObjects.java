package Metrics.Class;

import CodeModel.Dependencies.ClassReference.ImplementationDependency;
import CodeModel.Dependencies.ClassReference.InheritanceDependency;
import CodeModel.Dependencies.Dependency;
import Graph.CodeGraph;
import Graph.Edge.EdgeFilter;
import Graph.Node.ClassNode;
import Graph.Node.GraphNode;
import Graph.Structural.StructuralCodeGraph;

import java.util.Set;

public class CouplingBetweenObjects extends ClassMetric{

    public CouplingBetweenObjects(StructuralCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    @Override
    protected double calculate() {
        Set<GraphNode> coupledClasses = graph.getNeighboursByFilter(CodeGraph.EdgeDirection.OUT, classNode, (EdgeFilter) edge -> {
            for (Dependency dependency : edge.getDependencies()) {
                if ((dependency instanceof InheritanceDependency) || (dependency instanceof ImplementationDependency)) {
                    return false;
                }
            }
            return true;
        });

        addExtraInfoNodeList("Coupled classes", coupledClasses);
        return coupledClasses.size();
    }

    @Override
    public String getId() {
        return "CBO";
    }

}
