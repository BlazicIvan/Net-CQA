package Application;

import Application.Cli.CommandLineHandler;
import Graph.Export.GraphExportHelperFactory;
import Util.Log.Logging;

public class Launcher {

    public static void main(String[] args) {
        try {
            CommandLineHandler commandLineHandler = new CommandLineHandler();

            if(commandLineHandler.parse(args)) {
                String sourceDir = commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_SOURCE);
                String outputDir = commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_OUTPUT);
                String projectName = commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_PROJECT_NAME);

                if(commandLineHandler.hasArgument(CommandLineHandler.OPTION_ID_VERBOSE)) {
                    Logging.enableLogging();
                }

                Controller controller = new Controller(projectName, sourceDir, outputDir);

                controller.performAnalysis();

                String graphFormat = GraphExportHelperFactory.DEFAULT_FORMAT;

                if(commandLineHandler.hasArgument(CommandLineHandler.OPTION_ID_GRAPH_FORMAT)) {
                    graphFormat = commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_GRAPH_FORMAT);
                }
                controller.exportGraphs(graphFormat);
                controller.exportReport();
            }

        } catch (Exception ex) {
            CommandLineHandler.printLine("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
