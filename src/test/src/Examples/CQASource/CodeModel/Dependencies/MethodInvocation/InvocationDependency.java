package CodeModel.Dependencies.MethodInvocation;

import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class InvocationDependency extends Dependency {

    private final CtMethod callingMethod;
    private final CtMethod calledMethod;

    public InvocationDependency(CtMethod callingMethod, CtMethod calledMethod) {
        this.callingMethod = callingMethod;
        this.calledMethod = calledMethod;
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitInvocationDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "calls";
    }

    @Override
    public CtElement getSourceElement() {
        return callingMethod;
    }

    @Override
    public CtElement getTargetElement() {
        return calledMethod;
    }

    @Override
    public String getSourceElementName() {
        return callingMethod.getSimpleName();
    }

    @Override
    public String getTargetElementName() {
        return calledMethod.getSimpleName();
    }
}
