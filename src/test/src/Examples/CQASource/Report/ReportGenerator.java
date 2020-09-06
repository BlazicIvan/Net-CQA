package Report;

import Configuration.ConfigurationManager;
import Grade.GradeOverview;
import Grade.Property;
import Metrics.Metric;
import Report.Composition.*;
import Util.Time;

import java.util.List;

public class ReportGenerator {

    private final GradeOverview gradeOverview;
    private final String projectName;
    private ReportFile reportFile;

    private final List<String> allMetricIds;
    private final ConfigurationManager configuration;

    public ReportGenerator(GradeOverview gradeOverview, String projectName) {
        this.gradeOverview = gradeOverview;
        this.projectName = projectName;
        reportFile = null;
        allMetricIds = gradeOverview.getAllMetricIds();
        configuration = ConfigurationManager.getInstance();
    }

    private ReportElement buildProperties() {
        ComplexReportElement properties = new ComplexReportElement();

        for(Property property : gradeOverview.getAllProperties()) {

            properties.addElement(new PropertyEntryReportElement(
                    property,
                    gradeOverview.getPropertyGrade(property)));
        }

        return properties;
    }

    private ReportElement buildClassMetrics() {
        ComplexReportElement packages = new ComplexReportElement();
        List<String> classes = gradeOverview.getAllClassNames();

        for (String className : classes) {
            ComplexReportElement classMetrics = new ComplexReportElement();

            for (Metric metric : gradeOverview.getAllMetricsForClass(className)) {
                classMetrics.addElement(new MetricValueReportElement(metric));
            }

            packages.addElement(new ClassEntryReportElement(className, classMetrics));
        }

        return packages;
    }

    private ReportElement buildSystemMetrics() {
        ComplexReportElement systemMetrics = new ComplexReportElement();

        for(Metric systemMetric : gradeOverview.getAllSystemMetrics()) {
            systemMetrics.addElement(new MetricValueReportElement(systemMetric));
        }

        return systemMetrics;
    }

    private ReportElement buildMetricDescriptions() {
        ComplexReportElement metricDescriptions = new ComplexReportElement();

        for(String metricId : allMetricIds) {
            MetricText metricText = configuration.getMetricText(metricId);
            metricDescriptions.addElement(new MetricDescriptionReportElement(metricText));
        }

        return metricDescriptions;
    }

    private void buildReport() {
        String currentDate = Time.getCurrentDateTimeString();

        ReportElement root = new MainReportElement(
                projectName,
                currentDate,
                buildMetricDescriptions(),
                buildSystemMetrics(),
                buildClassMetrics(),
                buildProperties(),
                gradeOverview);

        reportFile = new ReportFile(root.generate());
    }

    public ReportFile generateReport() {
        if (reportFile == null) {
            buildReport();
        }

        return reportFile;
    }

}
