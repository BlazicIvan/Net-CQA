package CodeModel.Dependencies.MemberDefinitions;

import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;

public class FieldDefinitionDependency extends Dependency {

    private final CtType ctType;
    private final CtVariable ctVariable;

    public FieldDefinitionDependency(CtType ctType, CtVariable ctVariable) {
        this.ctType = ctType;
        this.ctVariable = ctVariable;
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitFieldDefinitionDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "has field";
    }

    @Override
    public CtType getSourceElement() {
        return ctType;
    }

    @Override
    public CtVariable getTargetElement() {
        return ctVariable;
    }

    @Override
    public String getSourceElementName() {
        return ctType.getSimpleName();
    }

    @Override
    public String getTargetElementName() {
        return ctVariable.getSimpleName();
    }
}
