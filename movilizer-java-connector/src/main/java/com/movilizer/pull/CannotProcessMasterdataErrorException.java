package com.movilizer.pull;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public class CannotProcessMasterdataErrorException extends Exception {
    public CannotProcessMasterdataErrorException(String message) {
        super(message);

    }

    public CannotProcessMasterdataErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotProcessMasterdataErrorException(Throwable cause) {
        super(cause);
    }
}
