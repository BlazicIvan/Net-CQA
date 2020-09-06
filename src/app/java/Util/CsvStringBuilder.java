package Util;

public class CsvStringBuilder {

    private StringBuilder stringBuilder = null;
    private static final String SEPARATOR = ", ";


    public void addItem(String item) {
        if(stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        else {
            stringBuilder.append(SEPARATOR);
        }

        stringBuilder.append(item);
    }

    @Override
    public String toString() {
        if(stringBuilder == null) {
            return "";
        }
        return stringBuilder.toString();
    }
}
