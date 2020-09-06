package Application.Cli.Test;

import Application.Cli.CommandLineHandler;
import Util.FileHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CommandLineHandlerTest {

    private static final String GOOD_OUTPUT = FileHelper.getWorkingDir();
    private static final String GOOD_SOURCE = FileHelper.getWorkingDir();


    @Test
    void basicFlow1() {
        String[] args = {"-s", GOOD_SOURCE, "-o", GOOD_OUTPUT, "-n", "Test project"};
        CommandLineHandler commandLineHandler = new CommandLineHandler();

        assertTrue(commandLineHandler.parse(args));
        assertEquals(GOOD_SOURCE, commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_SOURCE));
        assertEquals(GOOD_OUTPUT, commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_OUTPUT));
    }

    @Test
    void basicFlow2() {
        String[] args = {"--"+CommandLineHandler.OPTION_ID_OUTPUT, GOOD_OUTPUT,
                        "--"+CommandLineHandler.OPTION_ID_SOURCE, GOOD_SOURCE,
                        "--"+CommandLineHandler.OPTION_ID_PROJECT_NAME, "Test project"};
        CommandLineHandler commandLineHandler = new CommandLineHandler();

        assertTrue(commandLineHandler.parse(args));
        assertEquals(GOOD_SOURCE, commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_SOURCE));
        assertEquals(GOOD_OUTPUT, commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_OUTPUT));
    }

    @Test
    void missingAllArguments() {
        String[] args = {};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        assertFalse(commandLineHandler.parse(args));
    }


    @Test
    void missingSource() {
        String[] args = {"-o", GOOD_OUTPUT};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        assertFalse(commandLineHandler.parse(args));
    }

    @Test
    void missingOutput() {
        String[] args = {"-s", GOOD_SOURCE};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        assertFalse(commandLineHandler.parse(args));
    }

    @Test
    void badArgumentsTest() {
        String[] args = {"--output", "-efsdfsdfs00", "--source", ""};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        assertFalse(commandLineHandler.parse(args));
    }

    @Test
    void helpTest() {
        String[] args = {"-h"};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        assertFalse(commandLineHandler.parse(args));
    }

    @Test
    void verboseOutputOptionTest() {
        String[] args = {"-s", GOOD_SOURCE, "-o", GOOD_OUTPUT, "-n", "Test project", "-v"};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        commandLineHandler.parse(args);

        assertTrue(commandLineHandler.hasArgument(args[6]));
    }

    @Test
    void exportFormatTest() {
        String[] args = {"-s", GOOD_SOURCE, "-o", GOOD_OUTPUT, "-n", "Test project", "-f", "gexf"};

        CommandLineHandler commandLineHandler = new CommandLineHandler();
        commandLineHandler.parse(args);

        assertEquals(args[7], commandLineHandler.getArgumentValue(CommandLineHandler.OPTION_ID_GRAPH_FORMAT));
    }

}
