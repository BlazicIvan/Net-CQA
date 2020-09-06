package Report.Composition;

import Util.MathUtils;

public class DecimalNumberReportElement extends ReportElement {

    private final Double number;

    public DecimalNumberReportElement(Double number) {
        this.number = number;
    }

    @Override
    public String generate() {
        return Double.toString(MathUtils.roundValue(number, 2));
    }
}
