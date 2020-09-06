package Metrics.Test.Setup;

import CodeModel.CodeModel;
import Graph.GraphCollection;
import Metrics.MetricsCollection;
import CodeModel.CodeModelBuilder;
import Util.FileHelper;
import Util.Log.Logging;

import java.util.ArrayList;
import java.util.List;

public class MetricTestSetup {

    private final MetricsCollection metricsCollection;
    private List<String> sourceFiles;

    private void addFilesToTest(String[] paths) {
        sourceFiles = new ArrayList<>();

        for(String path: paths) {

            String baseSourcePath = "../test/src/Metrics/Test/";
            if(path.endsWith("/*")) {
                path = path.substring(0, path.length() - 2);
                path = baseSourcePath + path;

                sourceFiles.addAll(FileHelper.findFilesInDirectory(path));
            }
            else {
                String fullPath = baseSourcePath + path + ".java";
                sourceFiles.add(fullPath);
            }
        }
    }

    public MetricTestSetup(String[] includedFiles) {
        addFilesToTest(includedFiles);
        Logging.enableLogger(Logging.getLogger(CodeModel.class));
        CodeModel model = new CodeModelBuilder().buildModel(sourceFiles);
        GraphCollection graphs = new GraphCollection(model);
        metricsCollection = new MetricsCollection(graphs);
    }

    public double getMetricValue(String id) {
        return metricsCollection.getResult(id,true);
    }

    public double getMetricValue(String id, String className) {
        String basePackage = "Metrics.Test";
        return metricsCollection.getResult(basePackage +"."+className, id, true);
    }
}
