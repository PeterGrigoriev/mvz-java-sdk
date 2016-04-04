package com.movilizer.util.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class StringLogger implements ILogger {

    private final StringWriter stringWriter = new StringWriter();
    private final PrintWriter out = new PrintWriter(stringWriter, true);

    @Override
    public void trace(String message) {
        out.println("TRACE: " + message);
    }

    @Override
    public void debug(String message) {
        out.println("DEBUG: " + message);
    }

    @Override
    public void info(String message) {
        out.println("INFO: " + message);
    }

    @Override
    public void warn(String message) {
        out.println("WARN: " + message);
    }

    @Override
    public void error(String message) {
        out.println("ERROR: " + message);
    }

    @Override
    public void error(Throwable throwable) {
        out.println("ERROR: " + throwable.getMessage());
        throwable.printStackTrace(out);
    }

    @Override
    public void error(String message, Throwable throwable) {
        error(message);
        error(throwable);
    }

    @Override
    public void fatal(String message) {
        out.println("FATAL: " + message);
    }

    @Override
    public void fatal(Throwable throwable) {
        out.println("FATAL: " + throwable.getMessage());
        throwable.printStackTrace(out);
    }

    @Override
    public String toString() {
        return stringWriter.toString();
    }
}
