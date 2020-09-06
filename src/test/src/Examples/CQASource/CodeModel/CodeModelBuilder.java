package CodeModel;

import CodeModel.Dependencies.ClassReference.*;
import CodeModel.Dependencies.FieldReference.InternalFieldUsageDependency;
import CodeModel.Dependencies.MemberDefinitions.EnumDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.FieldDefinitionDependency;
import CodeModel.Dependencies.MemberDefinitions.MethodDefinitionDependency;
import CodeModel.Dependencies.MethodInvocation.InvocationDependency;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtAbstractVisitor;
import spoon.support.reflect.declaration.CtClassImpl;
import spoon.support.reflect.declaration.CtConstructorImpl;
import spoon.support.reflect.declaration.CtFieldImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CodeModelBuilder extends CtAbstractVisitor {

    private CtModel ctModel;
    private CodeModel model;
    private final Launcher launcher;

    private static final String[] primitiveTypes = {
            "int",
            "byte",
            "char",
            "short",
            "int",
            "long",
            "float",
            "double",
            "boolean"
    };


    private boolean isFieldTypeReferenceValid(CtClassImpl parentClass, CtTypeReference typeReference) {
        return (!parentClass.getSimpleName().equals(typeReference.getSimpleName())
                && isTypeReferenceValid(typeReference));
    }

    private static CtClassImpl getParentClass(CtElement invocation) {
        CtElement parent = invocation.getParent();

        while (parent != null) {
            if (parent instanceof CtClassImpl) {
                break;
            }
            parent = parent.getParent();
        }

        return (CtClassImpl) parent;
    }

    private static CtMethodImpl getCallingMethod(CtElement element) {
        CtElement parent = element.getParent();

        while ((parent != null) && !(parent instanceof CtMethodImpl)) {
            if(parent instanceof CtConstructorImpl) {
                return null;
            }

            parent = parent.getParent();
        }

        return (CtMethodImpl)parent;
    }


    private boolean isTypeReferenceValid(CtTypeReference typeReference) {

        boolean isValid = false;

        CtPackageReference elementPackage = typeReference.getPackage();

        if(elementPackage != null) {
            isValid = ctModel.getAllPackages().contains(elementPackage.getDeclaration());
        }

        return isValid;
    }

    private boolean isElementValid(CtElement element) {

        boolean isValid = true;

        if (element instanceof CtTypeInformation) {
            isValid = !Arrays.asList(primitiveTypes).contains(((CtTypeInformation)element).getQualifiedName());
        }

        if (element instanceof CtTypeReference) {
            isValid = isTypeReferenceValid((CtTypeReference) element);
        }

        return isValid;
    }

    private CodeModel buildCodeModel() {
        ctModel = launcher.buildModel();
        model = new CodeModel();

        List<CtElement> ctElements = ctModel.getElements(null);
        for(CtElement element : ctElements) {
            if (isElementValid(element)) {
                element.accept(this);
            }
        }

        return model;
    }

    @Override
    public <T> void visitCtField(CtField<T> field) {
        CtClassImpl parentClass = (CtClassImpl)field.getParent();

        CtTypeReference fieldType = field.getType();
        if(isFieldTypeReferenceValid(parentClass, fieldType)) {
            model.addDependency(new FieldTypeDependency(parentClass, fieldType));
        }

        for(CtTypeReference actualArg : fieldType.getActualTypeArguments()) {
            if(isFieldTypeReferenceValid(parentClass, actualArg)){
                model.addDependency(new FieldTypeDependency(parentClass,actualArg));
            }
        }

        CtType ctType = field.getTopLevelType();
        model.addDependency(new FieldDefinitionDependency(ctType,field));
    }

    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
        // Don't handle calls of own constructor
        if(!(invocation.getExecutable().getDeclaration() instanceof CtConstructorImpl)) {
            CtClassImpl callingType = getParentClass(invocation);
            if (callingType != null) {
                CtTypeReference declaringType = invocation.getExecutable().getDeclaringType();


                CtMethodImpl calledMethod = (CtMethodImpl) invocation.getExecutable().getDeclaration();
                CtMethodImpl callingMethod = getCallingMethod(invocation);

                if (declaringType != null) {
                    if (declaringType.getSimpleName().equals("void")) {
                        CtExecutableReference parent = (CtExecutableReference) declaringType.getParent();
                        while (parent.getDeclaringType() == null) {
                            parent = (CtExecutableReference) parent.getParent();
                        }
                        declaringType = parent.getDeclaringType();
                    }
                    if (declaringType != null && !(declaringType.getQualifiedName().equals(callingType.getQualifiedName()))
                            && isTypeReferenceValid(declaringType)) {
                        model.addDependency(new InvocationClassDependency(callingType, declaringType));
                    }
                }

                if (calledMethod != null && callingMethod != null) {
                    model.addDependency(new InvocationDependency(callingMethod, calledMethod));
                }
            }
        }
    }

    @Override
    public <T> void visitCtConstructorCall(CtConstructorCall<T> constructorCall) {
        CtClassImpl caller = getParentClass(constructorCall.getExecutable());
        CtTypeReference createdType = constructorCall.getType();

        if ((caller != null) && !(caller.equals(createdType.getTypeDeclaration())) && isTypeReferenceValid(createdType)) {
            model.addDependency(new InstantiationDependency(caller, createdType));
        }
    }

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {

        model.getElementNode(ctClass);

        CtTypeReference parentClass = ctClass.getSuperclass();
        if (parentClass != null && isTypeReferenceValid(parentClass)) {
            model.addDependency(new InheritanceDependency((CtClassImpl) ctClass, parentClass));
        }

        Set<CtTypeReference<?>> interfaces = ctClass.getSuperInterfaces();
        for (CtTypeReference interf : interfaces) {
            if (isTypeReferenceValid(interf)) {
                model.addDependency(new ImplementationDependency((CtClassImpl) ctClass, interf));
            }
        }
    }

    @Override
    public <T> void visitCtNewClass(CtNewClass<T> newClass) {
        CtClass innerClass = newClass.getAnonymousClass();
        CtClassImpl outerClass = getParentClass(innerClass);

        if (!outerClass.isEnum())  {
            String implementedName;
            CtTypeReference<?> superClass = innerClass.getSuperclass();
            Set<CtTypeReference<?>> superInterfaces = innerClass.getSuperInterfaces();

            if(superClass != null) {
                implementedName = superClass.getSimpleName();
            }
            else if (!superInterfaces.isEmpty()) {
                implementedName = superInterfaces.iterator().next().getSimpleName();
            }
            else {
                throw new RuntimeException(("Anonymous class of {} has " +
                        "no super class or interface!").replace("{}", outerClass.getSimpleName()));
            }

            innerClass.setSimpleName(outerClass.getSimpleName() + "::" +
                    implementedName + '#' +
                    innerClass.getSimpleName());

            innerClass.addModifier(ModifierKind.PRIVATE);

            model.getElementNode(innerClass);

            model.addDependency(new InnerClassDependency(outerClass, innerClass.getReference()));
        }
    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> ctMethod) {
        CtType ctType = getParentClass(ctMethod);
        if (ctType != null && isTypeReferenceValid(ctType.getReference())) {
            model.addDependency(new MethodDefinitionDependency(ctType, (CtMethodImpl) ctMethod));
        }
    }

    @Override
    public <T> void visitCtFieldReference(CtFieldReference<T> fieldReference) {
        CtMethodImpl usingMethod = getCallingMethod(fieldReference);
        CtField usedField = fieldReference.getDeclaration();

        if(usingMethod != null && usedField instanceof CtFieldImpl) {
            if(usingMethod.getTopLevelType().equals(usedField.getTopLevelType())) {
                model.addDependency(new InternalFieldUsageDependency(usingMethod, (CtFieldImpl) usedField));
            }
        }
    }

    @Override
    public <T extends Enum<?>> void visitCtEnum(CtEnum<T> ctEnum) {
        CtElement parent = ctEnum.getParent();
        if (parent instanceof CtClass) {
            CtClass parentClass = (CtClass)parent;
            model.addDependency(new EnumDefinitionDependency(ctEnum, parentClass));
        }
    }

    public CodeModelBuilder() {
        launcher = new Launcher();
    }

    public CodeModel buildModel(List<String> sourceFiles) {

        for(String source : sourceFiles) {
            launcher.addInputResource(source);
        }

        return buildCodeModel();
    }

    public CodeModel buildModel(String path) {
        launcher.addInputResource(path);

        return buildCodeModel();
    }


}
