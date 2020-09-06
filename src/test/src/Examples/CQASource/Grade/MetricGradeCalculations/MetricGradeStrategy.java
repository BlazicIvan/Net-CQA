package Grade.MetricGradeCalculations;

public abstract class MetricGradeStrategy {

    public static final String REF_EXCEED = "ref-exceed";
    public static final String RAW_VALUE = "raw-value";

    public abstract double calculateGrade(double realValue, double referenceValue);
}
