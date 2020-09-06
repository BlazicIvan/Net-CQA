package Application.Test;

import Application.Controller;
import Configuration.ConfigurationManager;
import Graph.Export.GraphExportHelperFactory;
import Util.Log.Logging;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ControllerTest {

    private static final Logger logger = Logging.getLogger(ControllerTest.class);

    private void runControllerTest(String projectName, String sourcePath, String outputPath) throws Exception {

        ConfigurationManager.reset();

        Controller controller = new Controller(
                projectName,
                sourcePath,
                outputPath);

        Logging.enableLogging();
        controller.performAnalysis();
        controller.exportGraphs(GraphExportHelperFactory.DEFAULT_FORMAT);
        controller.exportReport();
    }

    private void runSmokeTest(String testName) {
        String sourcePath = "../test/src/Examples/" + testName;
        String exportPath = System.getProperty("user.dir").concat("../../../build/test/exported");


        try {
            runControllerTest(testName, sourcePath, exportPath);
        }
        catch (Exception ex) {
            logger.severe(ex.getMessage());
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void badPaths() {
        String sourcePath = "";
        String exportPath = "/a/sd";

        try {
            runControllerTest("", sourcePath, exportPath);
            fail();
        }
        catch (Exception ex) {
            assertTrue(true);
        }
    }

    @Test
    public void badArchitecture() {
        runSmokeTest("BadArchitecture");
    }

    @Test
    public void javaSyntax() {
        runSmokeTest("JavaSyntax");
    }

    @Test
    public void randomExample() {
        runSmokeTest("RandomExample");
    }

    @Test
    public void demoExample() {
        runSmokeTest("DemoExample");
    }

    @Test
    public void cqaSource() {
        runSmokeTest("CQASource");
    }
}
