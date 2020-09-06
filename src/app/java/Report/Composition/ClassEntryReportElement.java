package Report.Composition;

public class ClassEntryReportElement extends TemplateReportElement {

    private final String className;
    private final ReportElement classMetrics;

    public ClassEntryReportElement(String className, ReportElement classMetrics) {
        this.className = className;
        this.classMetrics = classMetrics;
    }

    @Override
    protected String getFileName() {
        return "class_entry.html";
    }

    @Override
    public void replaceAllTags() {
        replace("class_name", new PlainTextReportElement(className));
        replace("class_metrics", classMetrics);
    }
}