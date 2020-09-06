package CodeModel.Dependencies.MemberDefinitions;

import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnum;

public class EnumDefinitionDependency extends Dependency {

    private CtEnum ctEnum;
    private CtClass ctClass;

    public EnumDefinitionDependency(CtEnum ctEnum, CtClass ctClass) {
        this.ctEnum = ctEnum;
        this.ctClass = ctClass;
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitEnumDefinitionDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "defines enum";
    }

    @Override
    public CtElement getSourceElement() {
        return ctClass;
    }

    @Override
    public CtElement getTargetElement() {
        return ctEnum;
    }

    @Override
    public String getSourceElementName() {
        return ctEnum.getSimpleName();
    }

    @Override
    public String getTargetElementName() {
        return ctClass.getSimpleName();
    }
}
