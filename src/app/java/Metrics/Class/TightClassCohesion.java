package Metrics.Class;


import Graph.CodeGraph.EdgeDirection;
import Graph.Node.*;
import Graph.Structural.ClassCodeGraph;
import Util.Log.Logging;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static spoon.reflect.declaration.ModifierKind.PUBLIC;

public class TightClassCohesion extends ClassMetric {

    private static final Logger logger = Logging.getLogger(TightClassCohesion.class);

    public TightClassCohesion(ClassCodeGraph graph, ClassNode classNode) {
        super(graph, classNode);
    }


    @Override
    protected double calculate() {
        double result = 0;

        // Number of connected public methods
        double ndc = 0;

        Set<MethodDefinitionNode> publicMethods = ((ClassCodeGraph)(graph)).getPublicMethodNodes(classNode);

        double numOfPublicMethods = publicMethods.size();

        // Maximal number of connections between public methods
        double np = (numOfPublicMethods * (numOfPublicMethods - 1)) / 2;

        Set<FieldNode> fields = ((ClassCodeGraph)(graph)).getFieldNodes(classNode);
        Set<Set<MethodDefinitionNode>> connectedMethodPairs = new HashSet<>();

        for(FieldNode var : fields) {

            // Find public methods that are using this member variable
            Set<MethodDefinitionNode> usingMethods = graph.getNeighboursByFilter(EdgeDirection.IN, var, new NodeFilter() {
                @Override
                public boolean acceptNode(GraphNode node) {
                    return node instanceof MethodDefinitionNode;
                }
            })
                    .stream().map(node -> (MethodDefinitionNode)node)
                    .filter(methodDefinitionNode -> methodDefinitionNode.getCtMethod().hasModifier(PUBLIC))
                    .filter(methodDefinitionNode -> publicMethods.contains(methodDefinitionNode))
                    .collect(Collectors.toSet());

            Object[] methodArray = usingMethods.toArray();

            // Find all connected methods
            for (int i=0; i < methodArray.length - 1; i++) {
                for(int j = i + 1; j < methodArray.length; j++) {

                    MethodDefinitionNode m1 = (MethodDefinitionNode)methodArray[i];
                    MethodDefinitionNode m2 = (MethodDefinitionNode)methodArray[j];

                    Set<MethodDefinitionNode> connectedPair = new HashSet<>();
                    connectedPair.add(m1);
                    connectedPair.add(m2);

                    if(!connectedMethodPairs.contains(connectedPair)) {
                        logger.info(m1.getElementName() + " and " + m2.getElementName()
                        + " connected via " + var.getElementName());

                        // Found new pair of connected methods
                        ndc += 1;
                        connectedMethodPairs.add(connectedPair);
                    }
                }
            }

            addExtraInfoNodeList("Methods using the field " + var.getElementName(),
                    usingMethods.stream().map(node -> (GraphNode)node).collect(Collectors.toSet()));
        }



        if(np != 0) {
            result = ndc / np;
        }

        return result;
    }



    @Override
    public String getId() {
        return "TCC";
    }

}
