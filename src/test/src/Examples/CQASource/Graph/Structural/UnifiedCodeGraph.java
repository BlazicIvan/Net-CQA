package Graph.Structural;

import CodeModel.CodeModel;
import CodeModel.Dependencies.ClassReference.*;
import CodeModel.Dependencies.DependencyVisitor;
import CodeModel.Dependencies.FieldReference.InternalFieldUsageDependency;
import CodeModel.Dependencies.MemberDefinitions.EnumDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.FieldDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.MethodDefinitionDependency;
import CodeModel.Dependencies.MethodInvocation.InvocationDependency;

public class UnifiedCodeGraph extends StructuralCodeGraph {

    public UnifiedCodeGraph(String name, CodeModel model) {
        super(name, model);
    }

    @Override
    protected DependencyVisitor getDependencyFilter() {
        return new DependencyVisitor() {
            @Override
            public boolean visitFieldTypeDependency(FieldTypeDependency dependency) {
                return true;
            }

            @Override
            public boolean visitImplementationDependency(ImplementationDependency dependency) {
                return true;
            }

            @Override
            public boolean visitInheritanceDependency(InheritanceDependency dependency) {
                return true;
            }

            @Override
            public boolean visitInnerClassDependency(InnerClassDependency dependency) {
                return true;
            }

            @Override
            public boolean visitInstantiationDependency(InstantiationDependency dependency) {
                return true;
            }

            @Override
            public boolean visitInvocationClassDependency(InvocationClassDependency dependency) {
                return true;
            }

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

            @Override
            public boolean visitInvocationDependency(InvocationDependency dependency) {
                return true;
            }
        };
    }
}
