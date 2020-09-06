package Util;

import org.apache.commons.lang3.StringUtils;


public class MultilineStringBuilder {

    private final StringBuilder stringBuilder;
    private static final int SEPARATOR_LEN = 100;
    private static final char SEPARATOR_CHAR = '-';

    public MultilineStringBuilder() {
        stringBuilder = new StringBuilder();
    }

    public MultilineStringBuilder(boolean addSeparatorOnStart) {
        this();

        if(addSeparatorOnStart) {
            addSeparator();
        }
    }

    public void addTextLine(String line) {
        stringBuilder.append(line).append('\n');
    }

    public void addSeparator() {
        addTextLine(StringUtils.repeat(SEPARATOR_CHAR,SEPARATOR_LEN));
    }

    public void addValueDescriptionLine(int value, String description) {
        addTextLine(description + ": " + value);
    }

    public void addValueDescriptionLine(double value, String description) {
        addTextLine(description + ": " + value);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
