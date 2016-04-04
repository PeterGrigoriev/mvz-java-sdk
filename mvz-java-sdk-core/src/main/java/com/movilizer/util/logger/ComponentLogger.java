package com.movilizer.util.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ComponentLogger implements ILogger {


    /**
     log4j.properties may be stored outside of the jar.
     Option 1. place log4j.properties nearby the jar
     Option 2. specify "log4j.configuration" system variablem e.g.
     java -jar -Dlog4j.configuration=c:\logs\log4j.debug.properties MovilizerRunner.jar -push
     */
    static {
        String filename = System.getProperty("log4j.configuration");
        if (null == filename || filename.trim().equals("")) {
            filename = "log4j.properties";
        }
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("Using prepackaged log4j properties file. Custom file: '" + file.getAbsolutePath() + "' not found.");
        } else {
            PropertyConfigurator.configure(file.getAbsolutePath());
        }

    }

    private static final Map<String, ILogger> componentToLoggerMap = new HashMap<String, ILogger>();
    private final Logger logger;

    public static ILogger getInstance(String component) {
        ILogger logger = componentToLoggerMap.get(component);
        if (null == logger) {
            logger = new ComponentLogger(component);
            componentToLoggerMap.put(component, logger);
        }

        return logger;
    }

    public static ILogger getInstance(Class aClass) {
        return getInstance(aClass.getName());
    }

    private ComponentLogger(String component) {
        this.logger = Logger.getLogger(component);
    }

    @Override
    public void trace(String message) {
        logger.trace(message);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(Throwable cause) {
        if (cause == null) {
            error("NULL cause being logged");
        } else {
            logger.error(cause.getMessage(), cause);
        }
    }

    @Override
    public void error(String message, Throwable cause) {
        if (cause == null) {
            error(message);
            warn("NULL cause being logged");
        } else {
            logger.error(message, cause);
        }
    }

    @Override
    public void fatal(String message) {
        logger.fatal(message);
    }

    @Override
    public void fatal(Throwable cause) {
        if (cause == null) {
            fatal("NULL cause being logged");
        } else {
            logger.fatal(cause.getMessage(), cause);
        }
    }
}
