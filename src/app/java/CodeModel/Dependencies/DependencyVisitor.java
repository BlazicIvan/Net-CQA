package CodeModel.Dependencies;

import CodeModel.Dependencies.ClassReference.*;
import CodeModel.Dependencies.FieldReference.InternalFieldUsageDependency;
import CodeModel.Dependencies.MemberDefinitions.EnumDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.FieldDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.MethodDefinitionDependency;
import CodeModel.Dependencies.MethodInvocation.InvocationDependency;

public abstract class DependencyVisitor {

    public boolean visitFieldTypeDependency(FieldTypeDependency dependency) {
        return false;
    }

    public boolean visitImplementationDependency(ImplementationDependency dependency) {
        return false;
    }

    public boolean visitInheritanceDependency(InheritanceDependency dependency) {
        return false;
    }

    public boolean visitInnerClassDependency(InnerClassDependency dependency) {
        return false;
    }

    public boolean visitInstantiationDependency(InstantiationDependency dependency) {
        return false;
    }

    public boolean visitInvocationClassDependency(InvocationClassDependency dependency) {
        return false;
    }

    public boolean visitInternalFieldUsageDependency(InternalFieldUsageDependency dependency) {
        return false;
    }

    public boolean visitEnumDefinitionDependency(EnumDefinitionDependency dependency) {
        return false;
    }

    public boolean visitFieldDefinitionDependency(FieldDefinitionDependency dependency) {
        return false;
    }

    public boolean visitMethodDefinitionDependency(MethodDefinitionDependency dependency){
        return false;
    }

    public boolean visitInvocationDependency(InvocationDependency dependency) {
        return false;
    }

}
