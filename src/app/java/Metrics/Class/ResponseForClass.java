package Metrics.Class;

import Graph.CodeGraph;
import Graph.Node.ClassNode;
import Graph.Node.GraphNode;
import Graph.Node.MethodDefinitionNode;
import Graph.Node.NodeFilter;
import Graph.Structural.CallCodeGraph;
import Graph.Structural.ClassCodeGraph;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseForClass extends ClassMetric {

    private final CallCodeGraph callGraph;

    public ResponseForClass(ClassCodeGraph graph, CallCodeGraph callGraph, ClassNode classNode) {
        super(graph, classNode);
        this.callGraph = callGraph;
    }

    @Override
    protected double calculate() {

        ClassCodeGraph classGraph = (ClassCodeGraph)graph;

        Set<MethodDefinitionNode> publicMethods = classGraph.getPublicMethodNodes(classNode);
        Set<GraphNode> methodsCalled = new HashSet<>();

        int numOfPublicMethods = publicMethods.size();

        for(MethodDefinitionNode method : publicMethods) {
            methodsCalled.addAll(callGraph.getNeighboursByFilter(CodeGraph.EdgeDirection.OUT, method,
                    (NodeFilter)  methodNode -> (methodNode instanceof MethodDefinitionNode &&
                            !classGraph.getMethodNodesByFilter(classNode, node -> true).contains(methodNode))));
        }

        Set<GraphNode> publicMethodNodes = publicMethods.stream().map(node -> (GraphNode)node).collect(Collectors.toSet());
        addExtraInfoNodeList("Public methods", publicMethodNodes);
        addExtraInfoNodeList("Methods called from this class", methodsCalled);

        return numOfPublicMethods + methodsCalled.size();
    }

    @Override
    public String getId() {
        return "RFC";
    }

}
