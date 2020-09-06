package Graph.Structural;

import CodeModel.CodeModel;
import CodeModel.Dependencies.DependencyVisitor;
import CodeModel.Dependencies.FieldReference.InternalFieldUsageDependency;
import CodeModel.Dependencies.MemberDefinitions.EnumDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.MethodDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.FieldDefinitionDependency;
import Graph.Node.*;
import spoon.reflect.declaration.ModifierKind;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassCodeGraph extends StructuralCodeGraph {

    public ClassCodeGraph(String name, CodeModel model) {
        super(name, model);
    }

    public Set<ClassNode> getClassNodes() {
        return graph.vertexSet().stream().filter(node -> node instanceof ClassNode).map(node -> (ClassNode)node)
                .collect(Collectors.toSet());
    }

    public Set<MethodDefinitionNode> getPublicMethodNodes(ClassNode classNode) {
        return getMethodNodesByFilter(classNode, node -> (ModifierKind.PUBLIC.equals(node.getAccessModifier())));
    }

    public Set<FieldNode> getFieldNodes(ClassNode classNode) {
        return getNeighboursByFilter(EdgeDirection.OUT, classNode, new NodeFilter() {
            @Override
            public boolean acceptNode(GraphNode node) {
                return node instanceof FieldNode;
            }
        }).stream().map(node -> (FieldNode)node).collect(Collectors.toSet());
    }

    public Set<MethodDefinitionNode> getMethodNodesByFilter(ClassNode parentClass, NodeFilter filter) {
        return getNeighboursByFilter(EdgeDirection.OUT, parentClass,
                (NodeFilter) node -> (node instanceof MethodDefinitionNode) && filter.acceptNode(node))
                .stream().map(node -> (MethodDefinitionNode)node).collect(Collectors.toSet());
    }

    @Override
    protected DependencyVisitor getDependencyFilter() {
        return new DependencyVisitor() {
            @Override
            public boolean visitInternalFieldUsageDependency(InternalFieldUsageDependency dependency) {
                return true;
            }

            @Override
            public boolean visitEnumDefinitionDependency(EnumDefinitionDependency dependency) {
                return true;
            }

            @Override
            public boolean visitFieldDefinitionDependency(FieldDefinitionDependency dependency) {
                return true;
            }

            @Override
            public boolean visitMethodDefinitionDependency(MethodDefinitionDependency dependency) {
                return true;
            }
        };
    }
}
