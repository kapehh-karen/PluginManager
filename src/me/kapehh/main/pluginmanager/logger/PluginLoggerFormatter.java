package me.kapehh.main.pluginmanager.logger;

import me.kapehh.main.pluginmanager.constants.ConstantSystem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Created by Karen on 26.07.2014.
 */
public class PluginLoggerFormatter extends SimpleFormatter {
    private DateFormat dateFormat = DateFormat.getDateTimeInstance();

    @Override
    public synchronized String format(LogRecord record) {
        StringBuffer buf = new StringBuffer(180);

        buf.append("[")
            .append(dateFormat.format(new Date(record.getMillis())))
            .append("] ")
            .append(formatMessage(record))
            .append(ConstantSystem.lineSep);

        /*Throwable throwable = record.getThrown();
        if (throwable != null) {
            StringWriter sink = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sink, true));
            buf.append(sink.toString());
        }*/
        return buf.toString();
    }
}