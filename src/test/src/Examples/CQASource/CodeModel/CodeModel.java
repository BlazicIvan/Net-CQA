package CodeModel;

import CodeModel.Dependencies.Dependency;
import Graph.Node.*;
import Util.Log.Logging;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.CtAbstractVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class CodeModel {

    private final List<Dependency> dependencies;
    private final HashMap<CtElement, GraphNode> nodeHashMap;

    private static final Logger logger = Logging.getLogger(CodeModel.class);

    private class CtElementNodeConverter extends CtAbstractVisitor {

        GraphNode graphNode;

        public GraphNode createNode(CtElement element) {
            element.accept(this);

            if (graphNode == null) {
                throw new RuntimeException("Uknown type of" + element.getShortRepresentation());
            }
            return graphNode;
        }


        @Override
        public <T> void visitCtInterface(CtInterface<T> intrface) {
            graphNode = new InterfaceNode(intrface);
        }

        @Override
        public <T> void visitCtClass(CtClass<T> ctClass) {
            graphNode = new ClassNode(ctClass);
        }

        @Override
        public <T> void visitCtMethod(CtMethod<T> m) {
            graphNode = new MethodDefinitionNode(m);
        }

        @Override
        public <T> void visitCtField(CtField<T> f) {
            graphNode = new FieldNode(f);
        }

        @Override
        public <T extends Enum<?>> void visitCtEnum(CtEnum<T> ctEnum) {
            graphNode = new EnumNode(ctEnum);
        }
    }

    public CodeModel() {
        dependencies = new ArrayList<>();
        nodeHashMap = new HashMap<>();
    }

    public void addDependency(Dependency dependency) {

        if(!dependencies.contains(dependency)) {
            dependencies.add(dependency);

            logger.info(dependency.toString());
        }
    }

    public List<Dependency> getAllDependencies() {
        return dependencies;
    }

    public GraphNode getElementNode(CtElement element) {
        if(!nodeHashMap.containsKey(element)) {

            CtElementNodeConverter converter = new CtElementNodeConverter();
            GraphNode graphNode = converter.createNode(element);

            nodeHashMap.put(element, graphNode);
            logger.info("Added node: " + graphNode.getElementName());
        }

        return nodeHashMap.get(element);
    }

    public List<GraphNode> getAllNodes() {
        return new ArrayList<>(nodeHashMap.values());
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("CodeModel model:\n");

        for(Dependency dep : dependencies) {
            builder.append(dep.toString());
            builder.append('\n');
        }

        return builder.toString();
    }
}
