package Grade.MetricGradeCalculations;

public class RawValueMetricGradeStrategy extends MetricGradeStrategy {
    @Override
    public double calculateGrade(double realValue, double referenceValue) {
        return realValue;
    }
}
