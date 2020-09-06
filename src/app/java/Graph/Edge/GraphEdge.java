package Graph.Edge;

import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import Graph.Node.GraphNode;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class GraphEdge extends DefaultWeightedEdge {

    private static int nextId = 0;
    private final int id = nextId++;

    private final List<Dependency> dependencies;


    public GraphEdge() {
        dependencies = new LinkedList<>();
    }

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public String getLabel() {
        StringJoiner stringJoiner = new StringJoiner(",");
        dependencies.forEach(dependency -> stringJoiner.add(dependency.getRelationDescription()));
        return stringJoiner.toString();
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public boolean hasDependencies(DependencyVisitor markerVisitor) {
        boolean hasDependencies = false;
        for(Dependency dependency : dependencies) {
            if(dependency.accept(markerVisitor)) {
                hasDependencies = true;
                break;
            }
        }

        return hasDependencies;
    }

    public GraphNode getSource() {
        return (GraphNode) super.getSource();
    }

    public GraphNode getTarget() {
        return (GraphNode) super.getTarget();
    }

    public String getId() {
        return ""+id;
    }

    @Override
    public double getWeight() {
        return super.getWeight();
    }
}
