package Metrics.Class;

import Graph.Node.ClassNode;
import Graph.Structural.StructuralCodeGraph;
import Metrics.Metric;

public abstract class ClassMetric extends Metric {

    final ClassNode classNode;

    ClassMetric(StructuralCodeGraph graph, ClassNode classNode) {
        super(graph);
        this.classNode = classNode;
    }

    public String getShortClassName() {
        return classNode.getElementName();
    }

    public String getFullClassName() {
        return classNode.getQualifiedName();
    }

    protected abstract double calculate();

    @Override
    public String toString() {
        return classNode.getQualifiedName() + " " + super.toString();
    }
}
