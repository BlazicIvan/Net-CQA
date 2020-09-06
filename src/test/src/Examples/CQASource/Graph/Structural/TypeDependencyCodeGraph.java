package Graph.Structural;

import CodeModel.CodeModel;
import CodeModel.Dependencies.ClassReference.*;
import CodeModel.Dependencies.DependencyVisitor;

public class TypeDependencyCodeGraph extends StructuralCodeGraph {

    public TypeDependencyCodeGraph(String name, CodeModel model) {
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
        };
    }
}
