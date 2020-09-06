package Report.Composition;

public class PlainTextReportElement extends ReportElement {

    private final String text;

    public PlainTextReportElement(String text) {
        this.text = text;
    }

    @Override
    public String generate() {
        return text;
    }
}
