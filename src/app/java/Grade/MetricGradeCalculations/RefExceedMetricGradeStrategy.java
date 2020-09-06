package Grade.MetricGradeCalculations;

public class RefExceedMetricGradeStrategy extends MetricGradeStrategy {

    @Override
    public double calculateGrade(double realValue, double referenceValue) {
        if(Double.compare(realValue, referenceValue) > 0) {
            return referenceValue/realValue;
        }

        return 1.0;
    }
}
