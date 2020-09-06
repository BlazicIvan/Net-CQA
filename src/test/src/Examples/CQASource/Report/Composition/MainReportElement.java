package Report.Composition;

import Grade.GradeOverview;

public class MainReportElement extends TemplateReportElement {


    private final String projectName;
    private final String date;
    private final double finalGrade;
    private final double maxGrade;
    private final ReportElement metricDescriptions;
    private final ReportElement systemMetrics;
    private final ReportElement classMetrics;
    private final ReportElement properties;

    public MainReportElement(String projectName,
                             String date,
                             ReportElement metricDescriptions,
                             ReportElement systemMetrics,
                             ReportElement classMetrics,
                             ReportElement properties,
                             GradeOverview gradeOverview) {
        this.projectName = projectName;
        this.date = date;
        this.metricDescriptions = metricDescriptions;
        this.systemMetrics = systemMetrics;
        this.classMetrics = classMetrics;
        this.properties = properties;
        this.finalGrade = gradeOverview.getFinalGrade();
        this.maxGrade = gradeOverview.getMaxGrade();
    }

    @Override
    protected String getFileName() {
        return "report_template.html";
    }

    @Override
    public void replaceAllTags() {
        replace("project_name", new PlainTextReportElement(projectName));
        replace("date", new PlainTextReportElement(date));
        replace("metric_descriptions", metricDescriptions);
        replace("system_metrics", systemMetrics);
        replace("class_metrics", classMetrics);
        replace("properties", properties);
        replace("final_grade", new DecimalNumberReportElement(finalGrade));
        replace("max_grade", new DecimalNumberReportElement(maxGrade));
        replace("report_description", new PlainTextFileReportElement("report_description.html"));
    }
}
