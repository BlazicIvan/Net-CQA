package Report.Composition;

import Report.MetricText;

public class MetricDescriptionReportElement extends TemplateReportElement {

    private final MetricText metricText;

    public MetricDescriptionReportElement(MetricText metricText) {
        this.metricText = metricText;
    }

    @Override
    protected String getFileName() {
        return "metric_description.html";
    }

    @Override
    public void replaceAllTags() {
        replace("title", new PlainTextReportElement(metricText.getTitle()));
        replace("id", new PlainTextReportElement(metricText.getMetricId()));
        replace("description", new PlainTextReportElement(metricText.getDescription()));
    }
}
