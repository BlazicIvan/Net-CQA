package Grade;

public class MetricFactor {

    private final String metricId;
    private final double value;

    public MetricFactor(String metricId, double value) {
        this.metricId = metricId;
        this.value = value;
    }

    public String getMetricId() {
        return metricId;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return metricId + ": " + value;
    }
}
