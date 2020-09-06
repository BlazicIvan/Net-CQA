package Report.Composition;

public class PlainTextFileReportElement extends TemplateReportElement {

    private final String fileName;

    public PlainTextFileReportElement(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected String getFileName() {
        return fileName;
    }

    @Override
    public void replaceAllTags() {}
}
