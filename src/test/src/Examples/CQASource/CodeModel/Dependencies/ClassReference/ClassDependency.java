package CodeModel.Dependencies.ClassReference;

import CodeModel.Dependencies.Dependency;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

public abstract class ClassDependency extends Dependency {

    private final CtClassImpl sourceElement;
    private final CtTypeReference targetElement;


    public String getSourceElementName() {
        return sourceElement.getSimpleName();
    }

    public String getTargetElementName() {
        return targetElement.getSimpleName();
    }

    ClassDependency(CtClassImpl sourceElement, CtTypeReference targetElement) {
        this.sourceElement = sourceElement;
        this.targetElement = targetElement;
    }

    public CtClassImpl getSourceElement() {
        return sourceElement;
    }

    public CtType getTargetElement() {
        return targetElement.getTypeDeclaration();
    }

    @Override
    public String toString() {
        return sourceElement.getSimpleName()+" "+getRelationDescription()+" "+targetElement.getSimpleName();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
