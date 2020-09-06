package Report.Composition;

import Metrics.Metric;

public class MetricValueReportElement extends TemplateReportElement {

    private final Metric metric;

    public MetricValueReportElement(Metric metric) {
        this.metric = metric;
    }

    @Override
    protected String getFileName() {
        return "metric_value.html";
    }

    @Override
    public void replaceAllTags() {
        replace("name", new PlainTextReportElement(metric.getName()));
        replace("value", new DecimalNumberReportElement(metric.getResult()));
        replace("additional_info", new TableReportElement(metric.getExtraInfoMap()));
    }
}
