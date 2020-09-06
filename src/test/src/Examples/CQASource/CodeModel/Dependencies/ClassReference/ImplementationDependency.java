package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public class ImplementationDependency extends ClassDependency {
    @Override
    public String getRelationDescription() {
        return "implements";
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitImplementationDependency(this);
    }

    public ImplementationDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        super(sourceElement, targetElement);
    }
}
