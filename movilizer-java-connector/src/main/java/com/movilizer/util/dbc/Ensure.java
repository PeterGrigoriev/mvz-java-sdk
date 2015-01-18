package com.movilizer.util.dbc;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class Ensure {
    static final ILogger logger = ComponentLogger.getInstance("MovilizerUtils");

    public static void ensure(boolean success, String whatIsViolated) {
        if (!success) {
            String message = format("VIOLATED: {0}", whatIsViolated);
            logger.fatal(message);
            throw new IllegalArgumentException(message);
        }
    }

    public static void ensureNotNull(Object value, String name) {
        ensure(value != null, format("{0} cannot be null", name));
    }

    public static void ensureNotNullOrEmpty(CharSequence value, String name) {
        ensureNotNull(value, name);
        ensure(value.length() != 0, format("{0} cannot be empty", name));
    }
}
