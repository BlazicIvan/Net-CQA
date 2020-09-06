package Report.Composition;

import Configuration.ConfigurationManager;
import Util.FileHelper;

public abstract class TemplateReportElement extends ReportElement {

    protected abstract String getFileName();

    protected void openTemplateFile() {
        String templatesPath = ConfigurationManager.getInstance().getReportTemplatesPath();
        String localFilePath = FileHelper.getLocalFilePath(templatesPath + "/" + getFileName());
        getStringBuilder().append(FileHelper.loadTextFile(localFilePath));
    }

    public abstract void replaceAllTags();

    @Override
    public String generate() {
        openTemplateFile();
        replaceAllTags();

        return super.generate();
    }
}
