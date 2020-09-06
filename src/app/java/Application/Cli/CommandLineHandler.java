package Application.Cli;
import org.apache.commons.cli.*;

public class CommandLineHandler {

    private final Options cliOptions;
    private CommandLine commandLine;

    private static final String CLI_NAME = "CQA";

    public static final String OPTION_ID_SOURCE = "source";
    public static final String OPTION_ID_OUTPUT = "output";
    public static final String OPTION_ID_PROJECT_NAME = "project_name";
    public static final String OPTION_ID_HELP   = "help";
    public static final String OPTION_ID_VERBOSE = "verbose";
    public static final String OPTION_ID_GRAPH_FORMAT = "graph-format";

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(CLI_NAME, cliOptions);
    }

    private static final String[] REQUIRED_ARGUMENTS = {
            OPTION_ID_SOURCE,
            OPTION_ID_OUTPUT,
            OPTION_ID_PROJECT_NAME
    };

    public static void printLine(String line) {
        System.out.println(line);
    }

    private boolean checkRequiredArguments() {
        boolean hasRequiredArgs = true;

        if(commandLine != null) {
            for (String arg : REQUIRED_ARGUMENTS) {
                hasRequiredArgs &= commandLine.hasOption(arg);
            }
        }
        else {
            hasRequiredArgs = false;
        }

        return hasRequiredArgs;
    }

    private Options createOptions() {
        Options options = new Options();

        options.addOption("s", OPTION_ID_SOURCE, true,
                "Path to source code (required)");

        options.addOption("o", OPTION_ID_OUTPUT, true,
                "Path to output directory (required)");

        options.addOption("n", OPTION_ID_PROJECT_NAME, true,
                "Project name (required)");

        options.addOption("h", OPTION_ID_HELP, false,
                "Display help");

        options.addOption("f", OPTION_ID_GRAPH_FORMAT, true,
                "Graph export format [csv, dot, gexf, gml]");

        options.addOption("v", OPTION_ID_VERBOSE, false,
                "Enable verbose output");

        return options;
    }

    public CommandLineHandler() {
        cliOptions = createOptions();
        commandLine = null;
    }

    public boolean parse(String[] arguments) {
        boolean parseSuccessful = false;

        try {
            CommandLineParser parser = new DefaultParser();
            commandLine = parser.parse(cliOptions, arguments);

            if(commandLine.hasOption(OPTION_ID_HELP)) {
                printHelp();
            }
            else if(!checkRequiredArguments()) {
                throw new ParseException("Missing or invalid arguments!");
            }
            else {
                parseSuccessful = true;
            }

        } catch (ParseException e) {
            printLine(e.getMessage());
            printHelp();
        }

        return parseSuccessful;
    }

    public String getArgumentValue(String option) {
        return commandLine.getOptionValue(option);
    }

    public boolean hasArgument(String option) {
        return commandLine.hasOption(option);
    }

}
