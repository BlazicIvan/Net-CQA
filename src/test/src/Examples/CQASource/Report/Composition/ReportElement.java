package Report.Composition;

public abstract class ReportElement {

    private StringBuilder stringBuilder;

    private static final char TAG_CHAR = '$';

    protected StringBuilder getStringBuilder() {
        if(stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }

        return stringBuilder;
    }

    protected void replace(String tag, ReportElement element) {

        getStringBuilder();

        String tagString = TAG_CHAR + tag;

        int tagIndex = stringBuilder.indexOf(tagString);

        String generatedElement = element.generate();

        if(tagIndex != -1) {
            stringBuilder = stringBuilder.replace(
                    tagIndex,
                    tagIndex + tagString.length(),
                    generatedElement);
        }
        else {
            throw new RuntimeException("Tag not found!");
        }
    }

    public String generate() {
        return getStringBuilder().toString();
    }
}
