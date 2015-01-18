package com.movilizer.util.logger;

public interface ILogger {

    void trace(String message);

    void debug(String message);

    void info(String message);

    void warn(String message);

    void error(String message);

    void error(Throwable throwable);

    void error(String message, Throwable throwable);

    void fatal(String message);

    void fatal(Throwable throwable);
}
