package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public class InnerClassDependency extends ClassDependency {

    public InnerClassDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        super(sourceElement, targetElement);
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitInnerClassDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "has nested class";
    }
}
