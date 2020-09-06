package Graph.Structural;

import CodeModel.CodeModel;
import CodeModel.Dependencies.DependencyVisitor;
import CodeModel.Dependencies.MethodInvocation.InvocationDependency;
import Graph.Node.MethodDefinitionNode;
import Graph.Node.NodeFilter;

public class CallCodeGraph extends StructuralCodeGraph {

    public CallCodeGraph(String name, CodeModel model) {
        super(name, model);
    }
    
    @Override
    protected DependencyVisitor getDependencyFilter() {
        return new DependencyVisitor() {
            @Override
            public boolean visitInvocationDependency(InvocationDependency dependency) {
                return true;
            }
        };
    }

    @Override
    protected NodeFilter getNodeSupportFilter() {
        return node -> node instanceof MethodDefinitionNode;
    }
}


