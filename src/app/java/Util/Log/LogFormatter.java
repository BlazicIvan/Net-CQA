package Util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {

    private static final String LOG_FORMAT = "[%s %s] %s:\t%s\n";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        return String.format(LOG_FORMAT,
                record.getLevel().toString(),
                DATE_FORMAT.format(new Date(record.getMillis())),
                record.getLoggerName(),
                record.getMessage());
    }
}