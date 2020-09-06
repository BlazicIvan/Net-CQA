package Metrics.Class;

import Graph.CodeGraph.EdgeDirection;
import Graph.Node.ClassNode;
import Graph.Node.FieldNode;
import Graph.Node.GraphNode;
import Graph.Node.NodeFilter;
import Graph.Structural.ClassCodeGraph;

import java.util.Set;

public class NumOfMemberVars extends ClassMetric {

    public NumOfMemberVars(ClassCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }

    @Override
    protected double calculate() {
        Set<GraphNode> vars = graph.getNeighboursByFilter(EdgeDirection.OUT, classNode,
                (NodeFilter) node -> (node instanceof FieldNode));
        addExtraInfoNodeList("Variables", vars);

        return vars.size();
    }

    @Override
    public String getId() {
        return "NOVF";
    }

}
