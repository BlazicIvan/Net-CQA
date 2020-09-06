package Report.Composition;

import java.util.LinkedList;
import java.util.List;

public class ComplexReportElement extends ReportElement {

    private final List<ReportElement> elementList;

    public ComplexReportElement() {
        elementList = new LinkedList<>();
    }

    public void addElement(ReportElement element) {
        elementList.add(element);
    }

    @Override
    public String generate() {
        for (ReportElement element : elementList) {
            getStringBuilder().append(element.generate());
        }

        return super.generate();
    }
}
