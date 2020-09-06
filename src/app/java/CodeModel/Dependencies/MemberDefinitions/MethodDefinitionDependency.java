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

            if(operator.getLeftHandOperand() != null) {
                operator.getLeftHandOperand().accept(this);
            }

            if (operator.getRightHandOperand() != null) {
                operator.getRightHandOperand().accept(this);
            }
        }

        @Override
        public void visitCtIf(CtIf ctIf) {
            complexity++;

            if (ctIf.getCondition() != null) {
                ctIf.getCondition().accept(this);
            }

            if (ctIf.getThenStatement() != null) {
                ctIf.getThenStatement().accept(this);
            }

            if(ctIf.getElseStatement() != null) {
                ctIf.getElseStatement().accept(this);
            }
        }

        @Override
        public <S> void visitCtCase(CtCase<S> ctCase) {
            complexity++;

            CtExpression caseExpression = ctCase.getCaseExpression();
            if (caseExpression != null) {
                ctCase.getCaseExpression().accept(this);
            }
        }

        @Override
        public void visitCtThrow(CtThrow ctThrow) {
            complexity++;
            if(ctThrow.getThrownExpression() != null) {
                ctThrow.getThrownExpression().accept(this);
            }
        }

        @Override
        public void visitCtDo(CtDo ctDo) {
            complexity++;

            if (ctDo.getLoopingExpression() != null) {
                ctDo.getLoopingExpression().accept(this);
            }

            if (ctDo.getBody() != null) {
                ctDo.getBody().accept(this);
            }
        }

        @Override
        public void visitCtWhile(CtWhile ctWhile) {
            complexity++;

            if (ctWhile.getLoopingExpression() != null) {
                ctWhile.getLoopingExpression().accept(this);
            }

            if (ctWhile.getBody() != null) {
                ctWhile.getBody().accept(this);
            }
        }

        @Override
        public void visitCtFor(CtFor ctFor) {
            complexity++;

            if (ctFor.getExpression() != null) {
                ctFor.getExpression().accept(this);
            }

            if (ctFor.getBody() != null) {
                ctFor.getBody().accept(this);
            }
        }

        @Override
        public void visitCtForEach(CtForEach ctForEach) {
            complexity++;

            if(ctForEach.getExpression() != null) {
                ctForEach.getExpression().accept(this);
            }
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

            if (ctConditional.getCondition() != null) {
                ctConditional.getCondition().accept(this);
            }
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
        double cycloComplexity = 1;

        try {
            CyclomaticComplexityCalculator complexityCalculator = new CyclomaticComplexityCalculator();
            ctMethod.accept(complexityCalculator);

            cycloComplexity = complexityCalculator.getResult();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not calculate cyclomatic complexity of " + getTargetElementName());
        }

        return cycloComplexity;
    }
}
