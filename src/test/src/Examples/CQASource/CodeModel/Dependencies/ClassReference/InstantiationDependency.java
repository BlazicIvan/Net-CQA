package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public class InstantiationDependency extends ClassDependency {


    public InstantiationDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        super(sourceElement, targetElement);
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitInstantiationDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "creates";
    }
}
