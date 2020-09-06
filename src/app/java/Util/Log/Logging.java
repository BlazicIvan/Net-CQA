package Util.Log;

import Configuration.ConfigurationManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Logging {

    private static Map<String, Logger> loggerRegistry;

    private static Map<String, Logger> getLoggerRegistry() {
        if(loggerRegistry == null) {
            loggerRegistry = new HashMap<>();
        }

        return loggerRegistry;
    }

    public static void disableLogger(Logger logger) {
        for(Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
    }

    public static void enableLogger(Logger logger) {

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LogFormatter());


        logger.addHandler(handler);
    }


    public static Logger getLogger(Class myClass) {

        String loggerName = myClass.getSimpleName();

        Logger logger = Logger.getLogger(loggerName);
        logger.setUseParentHandlers(false);

        getLoggerRegistry().put(loggerName, logger);

        if((Boolean) ConfigurationManager.getInstance().getSetting(ConfigurationManager.SETTING_LOGGER_ENABLED)) {
            enableLogger(logger);
        }

        return logger;
    }

    public static void enableLogging() {
        for(Logger logger : getLoggerRegistry().values()) {
            enableLogger(logger);
        }

        ConfigurationManager.getInstance().setSetting(ConfigurationManager.SETTING_LOGGER_ENABLED, true);
    }
}
