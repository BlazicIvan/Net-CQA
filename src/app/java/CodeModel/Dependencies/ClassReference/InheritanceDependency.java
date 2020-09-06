package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public class InheritanceDependency extends ClassDependency {

    @Override
    public String getRelationDescription() {
        return "extends";
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitInheritanceDependency(this);
    }

    public InheritanceDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        super(sourceElement, targetElement);
    }


}
