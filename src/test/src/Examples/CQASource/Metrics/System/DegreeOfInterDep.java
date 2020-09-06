package Metrics.System;

import Graph.Structural.TypeDependencyCodeGraph;
import Metrics.Metric;

public class DegreeOfInterDep extends Metric {

    public DegreeOfInterDep(TypeDependencyCodeGraph graph) {
        super(graph);
    }

    @Override
    protected double calculate() {
        return graph.getDensity();
    }

    @Override
    public String getId() {
        return "DOI";
    }

}
