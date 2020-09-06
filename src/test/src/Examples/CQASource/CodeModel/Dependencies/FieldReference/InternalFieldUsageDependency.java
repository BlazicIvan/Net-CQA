package CodeModel.Dependencies.FieldReference;

import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.declaration.CtFieldImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

public class InternalFieldUsageDependency extends Dependency {

    private final CtMethodImpl source;
    private final CtFieldImpl target;


    public InternalFieldUsageDependency(CtMethodImpl source, CtFieldImpl target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitInternalFieldUsageDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "uses field";
    }

    @Override
    public CtElement getSourceElement() {
        return source;
    }

    @Override
    public CtElement getTargetElement() {
        return target;
    }

    @Override
    public String getSourceElementName() {
        return source.getSimpleName();
    }

    @Override
    public String getTargetElementName() {
        return target.getSimpleName();
    }
}
