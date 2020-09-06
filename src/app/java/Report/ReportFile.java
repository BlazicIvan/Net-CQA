package Report;

import Util.FileHelper;

public class ReportFile {

    private final String content;

    protected ReportFile(String content) {
        this.content = content;
    }

    public void export(String filePath) {
        FileHelper.saveTextToFile(filePath, content);
    }
}
