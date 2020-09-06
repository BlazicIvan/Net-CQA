package Application;

import Application.Cli.CommandLineHandler;
import CodeModel.CodeModel;
import CodeModel.CodeModelBuilder;
import Configuration.ConfigurationManager;
import Grade.GradeOverview;
import Grade.Property;
import Graph.Export.GraphExportHelper;
import Graph.Export.GraphExportHelperFactory;
import Graph.GraphCollection;
import Metrics.MetricsCollection;
import Report.ReportFile;
import Report.ReportGenerator;
import Util.FileHelper;
import org.jgrapht.io.ExportException;

import java.io.IOException;

public class Controller {

    private final String projectName;
    private final String sourcePath;
    private final String outputPath;

    private MetricsCollection metricsCollection;
    private GraphCollection graphCollection;

    private static final String REPORT_FILENAME = "report.html";
    private static final String GRAPH_DIR_NAME = "graphs";

    private void printToConsole(String line) {
        CommandLineHandler.printLine(line);
    }

    private void checkOutputDir() throws IOException {
        if(!FileHelper.checkAndCreateDirectory(outputPath)) {
            throw new IOException("Could not create output directory!");
        }
    }

    public Controller(String projectName, String sourcePath, String outputPath) {
        this.projectName = projectName;
        this.sourcePath = sourcePath;
        this.outputPath = FileHelper.appendPath(outputPath, projectName);
    }

    public void performAnalysis() {

        printToConsole("Running code analysis...");
        CodeModel model = new CodeModelBuilder().buildModel(sourcePath);

        printToConsole("Creating graphs...");
        graphCollection = new GraphCollection(model);

        printToConsole("Calculating metrics...");
        metricsCollection = new MetricsCollection(graphCollection);

        metricsCollection.logAllResults();
    }

    public void exportReport() throws IOException {

        checkOutputDir();

        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        double maxGrade = (double)configurationManager.getSetting(ConfigurationManager.SETTING_MAX_GRADE);

        printToConsole("Calculating grade...");
        GradeOverview gradeOverview = new GradeOverview(metricsCollection, maxGrade);

        printToConsole("Final grade: " + gradeOverview.getFinalGrade());

        for (Property property : gradeOverview.getAllProperties()) {
            printToConsole(property.getName() + " = " + gradeOverview.getPropertyGrade(property));
        }

        ReportGenerator reportGenerator = new ReportGenerator(
                gradeOverview,
                projectName);

        ReportFile reportFile = reportGenerator.generateReport();
        reportFile.export(FileHelper.appendPath(outputPath, REPORT_FILENAME));
    }

    public void exportGraphs(String format) throws IOException, ExportException {
        checkOutputDir();

        String graphsDirPath = FileHelper.appendPath(outputPath, GRAPH_DIR_NAME);
        if(FileHelper.checkAndCreateDirectory(graphsDirPath)) {
            GraphExportHelper exportHelper = GraphExportHelperFactory.createGraphExportHelper(format);
            graphCollection.exportAll(FileHelper.appendPath(outputPath, GRAPH_DIR_NAME), exportHelper);
        }
        else {
            throw new ExportException("Could not create graph export directory!");
        }
    }
}
