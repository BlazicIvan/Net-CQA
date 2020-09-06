package Metrics.System;

import Graph.Node.GraphNode;
import Graph.Structural.TypeDependencyCodeGraph;
import Metrics.Metric;

import java.util.Set;

public class NumOfCyclicDeps extends Metric {

    public NumOfCyclicDeps(TypeDependencyCodeGraph graph) {
        super(graph);
    }

    @Override
    protected double calculate() {
        Set<GraphNode> cyclicDependencies = graph.findCycles();
        addExtraInfoNodeList("Elements in circular dependencies", cyclicDependencies);

        return cyclicDependencies.size();
    }

    @Override
    public String getId() {
        return "NCDC";
    }

}
