package CodeModel.Dependencies;

import spoon.reflect.declaration.CtElement;

public abstract class Dependency {

    public abstract String getRelationDescription();

    public abstract CtElement getSourceElement();

    public abstract CtElement getTargetElement();

    public abstract String getSourceElementName();

    public abstract String getTargetElementName();

    public abstract boolean accept(DependencyVisitor visitor);

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (obj instanceof Dependency) {
            Dependency dep = (Dependency)obj;
            if (dep.getTargetElement() == null) {
                throw new RuntimeException("null target element: " + dep.getSourceElement()
                        +" " + dep.getRelationDescription());
            }
            if (dep.getSourceElement() == null) {
                throw new RuntimeException("null source element: " + dep.getTargetElement()
                        +" " + dep.getRelationDescription());
            }
            isEqual = (dep.getSourceElement().equals(getSourceElement()) &&
                        dep.getTargetElement().equals(getTargetElement()));
        }

        return isEqual;
    }

    @Override
    public String toString() {
        return getSourceElementName() + " " + getRelationDescription() + " " + getTargetElementName();
    }

    public double getEdgeWeight() {
        return 1;
    }
}
