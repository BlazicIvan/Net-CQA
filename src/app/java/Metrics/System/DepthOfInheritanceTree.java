package Metrics.System;

import CodeModel.Dependencies.ClassReference.ImplementationDependency;
import CodeModel.Dependencies.ClassReference.InheritanceDependency;
import CodeModel.Dependencies.DependencyVisitor;
import Graph.CodeGraph;
import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import Graph.Structural.TypeDependencyCodeGraph;
import Metrics.Metric;
import org.jgrapht.GraphPath;

import java.util.Set;

public class DepthOfInheritanceTree extends Metric {

    public DepthOfInheritanceTree(TypeDependencyCodeGraph graph) {
        super(graph);
    }

    @Override
    protected double calculate() {

        double maxDepthOfInheritance = 0;

        CodeGraph inheritanceGraph = graph.createProjection(edge -> edge.hasDependencies(new DependencyVisitor() {
            @Override
            public boolean visitInheritanceDependency(InheritanceDependency dependency) {
                return true;
            }

            @Override
            public boolean visitImplementationDependency(ImplementationDependency dependency) {
                return true;
            }
        }));

        Set<GraphNode> inheritanceRoots = inheritanceGraph.getOutgoingEdgeOnlyNodes();
        Set<GraphNode> inheritanceLeafs = inheritanceGraph.getIncomingEdgeOnlyNodes();

        GraphPath<GraphNode, GraphEdge> longestIneritancePath = null;

        for(GraphNode root : inheritanceRoots) {
            for(GraphPath<GraphNode,GraphEdge> path: inheritanceGraph.getAllSingleSourcePaths(root, inheritanceLeafs)) {

                if (path != null) {
                    if(longestIneritancePath == null) {
                        longestIneritancePath = path;
                    }
                    else {
                        if(path.getLength() > longestIneritancePath.getLength()) {
                            longestIneritancePath = path;
                        }
                    }
                }
            }
        }

        if(longestIneritancePath != null){
            maxDepthOfInheritance = longestIneritancePath.getLength();
            addExtraInfoNodeList("Longest inheritance path", longestIneritancePath.getVertexList());
        }

        return maxDepthOfInheritance;
    }

    @Override
    public String getId() {
        return "DIT";
    }

}
