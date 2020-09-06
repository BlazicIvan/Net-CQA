package CodeModel.Dependencies.MemberDefinitions;

import CodeModel.Dependencies.Dependency;
import CodeModel.Dependencies.DependencyVisitor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtScanner;
import spoon.support.reflect.declaration.CtMethodImpl;

public class MethodDefinitionDependency extends Dependency {

    private final CtType ctType;
    private final CtMethodImpl ctMethod;

    private class CyclomaticComplexityCalculator extends CtScanner {

        private int complexity = 1;

        public int getResult() {
            return complexity;
        }

        @Override
        public <T> void visitCtBinaryOperator(CtBinaryOperator<T> operator) {
            BinaryOperatorKind operatorKind = operator.getKind();
            if(operatorKind == BinaryOperatorKind.AND || operatorKind == BinaryOperatorKind.OR) {
                complexity++;
            }
            operator.getLeftHandOperand().accept(this);
            operator.getRightHandOperand().accept(this);
        }

        @Override
        public void visitCtIf(CtIf ctIf) {
            complexity++;
            ctIf.getCondition().accept(this);

            ctIf.getThenStatement().accept(this);
            if(ctIf.getElseStatement() != null) {
                ctIf.getElseStatement().accept(this);
            }
        }

        @Override
        public <S> void visitCtCase(CtCase<S> ctCase) {
            complexity++;
            ctCase.getCaseExpression().accept(this);
        }

        @Override
        public void visitCtThrow(CtThrow ctThrow) {
            complexity++;
            ctThrow.getThrownExpression().accept(this);
        }

        @Override
        public void visitCtDo(CtDo ctDo) {
            complexity++;
            ctDo.getLoopingExpression().accept(this);
            ctDo.getBody().accept(this);
        }

        @Override
        public void visitCtWhile(CtWhile ctWhile) {
            complexity++;
            ctWhile.getLoopingExpression().accept(this);
            ctWhile.getBody().accept(this);
        }

        @Override
        public void visitCtFor(CtFor ctFor) {
            complexity++;
            ctFor.getExpression().accept(this);
            ctFor.getBody().accept(this);
        }

        @Override
        public void visitCtForEach(CtForEach ctForEach) {
            complexity++;
            ctForEach.getExpression().accept(this);
        }

        @Override
        public void visitCtBreak(CtBreak ctBreak) {
            complexity++;
        }

        @Override
        public void visitCtContinue(CtContinue ctContinue) {
            complexity++;
        }

        @Override
        public <T> void visitCtConditional(CtConditional<T> ctConditional) {
            complexity++;
            ctConditional.getCondition().accept(this);
        }
    }


    public MethodDefinitionDependency(CtType ctType, CtMethodImpl ctMethod) {
        this.ctType = ctType;
        this.ctMethod = ctMethod;
    }

    @Override
    public boolean accept(DependencyVisitor visitor) {
        return visitor.visitMethodDefinitionDependency(this);
    }

    @Override
    public String getRelationDescription() {
        return "has method";
    }

    @Override
    public CtType getSourceElement() {
        return ctType;
    }

    @Override
    public CtMethodImpl getTargetElement() {
        return ctMethod;
    }

    @Override
    public String getSourceElementName() {
        return ctType.getSimpleName();
    }

    @Override
    public String getTargetElementName() {
        return ctMethod.getSimpleName();
    }

    @Override
    public double getEdgeWeight() {
        double cycloComplexity;

        CyclomaticComplexityCalculator complexityCalculator = new CyclomaticComplexityCalculator();
        ctMethod.accept(complexityCalculator);

        cycloComplexity = complexityCalculator.getResult();

        return cycloComplexity;
    }
}
