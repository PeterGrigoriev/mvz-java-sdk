package com.movilizer.pull;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public class CannotProcessMasterdataDeletionException extends Exception {
    public CannotProcessMasterdataDeletionException(String message) {
        super(message);

    }

    public CannotProcessMasterdataDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotProcessMasterdataDeletionException(Throwable cause) {
        super(cause);
    }
}
