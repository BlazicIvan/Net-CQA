package Grade.MetricGradeCalculations;

public class MetricGradeCalculator {

    private final MetricGradeStrategy strategy;
    private final double refValue;


    public MetricGradeCalculator(MetricGradeStrategy strategy, double refValue) {
        this.refValue = refValue;
        this.strategy = strategy;
    }

    public double calculateRelativeGrade(double realValue) {
        return strategy.calculateGrade(realValue, refValue);
    }

}
