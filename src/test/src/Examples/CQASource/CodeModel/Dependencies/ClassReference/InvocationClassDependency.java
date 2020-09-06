package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public class InvocationClassDependency extends ClassDependency {

    @Override
    public String getRelationDescription() {
        return "calls method of";
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitInvocationClassDependency(this);
    }

    public InvocationClassDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        super(sourceElement, targetElement);
    }
}
