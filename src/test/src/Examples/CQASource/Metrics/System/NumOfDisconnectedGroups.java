package Metrics.System;

import Graph.Node.GraphNode;
import Graph.Structural.TypeDependencyCodeGraph;
import Metrics.Metric;

import java.util.Set;

public class NumOfDisconnectedGroups extends Metric {

    public NumOfDisconnectedGroups(TypeDependencyCodeGraph graph) {
        super(graph);
    }

    @Override
    protected double calculate() {

        Set<Set<GraphNode>> allSubgraphs = graph.getWeaklyConnectedComponents();

        if(allSubgraphs.size() > 1) {
            int subgraphNum = 1;
            for (Set<GraphNode> subgraph : allSubgraphs) {
                addExtraInfoNodeList("Disconnected group #" + subgraphNum, subgraph);
                subgraphNum++;
            }
        }

        return allSubgraphs.size();
    }

    @Override
    public String getId() {
        return "NDG";
    }

}
