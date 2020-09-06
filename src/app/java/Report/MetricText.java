package Report;

public class MetricText {

    private final String metricId;
    private final String title;
    private final String description;

    public MetricText(String metricId, String title, String description) {
        this.metricId = metricId;
        this.title = title;
        this.description = description;
    }

    public String getMetricId() {
        return metricId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
