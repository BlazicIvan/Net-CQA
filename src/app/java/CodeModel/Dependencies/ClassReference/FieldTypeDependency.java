package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public class FieldTypeDependency extends ClassDependency {

    @Override
    public String getRelationDescription() {
        return "contains";
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitFieldTypeDependency(this);
    }

    public FieldTypeDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        super(sourceElement, targetElement);
    }
}
