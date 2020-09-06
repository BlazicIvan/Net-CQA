package Metrics.System;

import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import Graph.Structural.CallCodeGraph;
import Metrics.Metric;
import org.jgrapht.GraphPath;

public class MaxCallIndirection extends Metric {

    public MaxCallIndirection(CallCodeGraph graph) {
        super(graph);
    }

    @Override
    protected double calculate() {
        GraphPath<GraphNode, GraphEdge> longestPath = graph.getLongestDirectedPath();
        if (longestPath != null) {
            addExtraInfoNodeList("Longest call path", longestPath.getVertexList());
            return longestPath.getLength();
        }

        return 0;
    }

    @Override
    public String getId() {
        return "MCI";
    }

}
