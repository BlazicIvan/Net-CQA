package Report.Composition;

import Grade.Property;

public class PropertyEntryReportElement extends TemplateReportElement {

    private final Property property;
    private final double propertyGrade;

    public PropertyEntryReportElement(Property property, double propertyGrade) {
        this.property = property;
        this.propertyGrade = propertyGrade;
    }

    @Override
    protected String getFileName() {
        return "property_entry.html";
    }

    @Override
    public void replaceAllTags() {
        replace("property_name", new PlainTextReportElement(property.getName()));
        replace("property_grade", new DecimalNumberReportElement(propertyGrade));
    }
}
