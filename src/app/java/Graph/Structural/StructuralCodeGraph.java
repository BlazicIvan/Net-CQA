package Graph.Structural;

import CodeModel.CodeModel;
import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import Graph.CodeGraph;
import Graph.Edge.GraphEdge;
import Graph.Node.GraphNode;
import Graph.Node.NodeFilter;

import java.util.List;

public abstract class StructuralCodeGraph extends CodeGraph {

    public StructuralCodeGraph(String name, CodeModel model) {
        super(name, model);
    }

    protected abstract DependencyVisitor getDependencyFilter();

    protected NodeFilter getNodeSupportFilter() {
        // Default implementation, override in sub-class
        return node -> true;
    }


    protected void buildGraph() {
        List<Dependency> dependencies = model.getAllDependencies();

        for(GraphNode node : model.getAllNodes()) {
            if(!graph.containsVertex(node) && getNodeSupportFilter().acceptNode(node)) {
                graph.addVertex(node);
            }
        }

        for(Dependency dep : dependencies) {

            if(dep.accept(getDependencyFilter())) {
                GraphNode source = model.getElementNode(dep.getSourceElement());
                GraphNode target = model.getElementNode(dep.getTargetElement());


                if(!graph.containsVertex(source)) {
                    graph.addVertex(source);
                }

                if(!graph.containsVertex(target)) {
                    graph.addVertex(target);
                }

                GraphEdge edge = graph.getEdge(source, target);

                if (edge == null) {
                    edge = new GraphEdge();
                    graph.addEdge(source, target, edge);
                    graph.setEdgeWeight(edge, dep.getEdgeWeight());
                }

                edge.addDependency(dep);
            }
        }


    }

}
