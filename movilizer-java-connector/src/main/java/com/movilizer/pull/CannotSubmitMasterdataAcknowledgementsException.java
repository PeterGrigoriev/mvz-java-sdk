package com.movilizer.pull;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public class CannotSubmitMasterdataAcknowledgementsException extends Exception {
    public CannotSubmitMasterdataAcknowledgementsException(String message) {
        super(message);

    }

    public CannotSubmitMasterdataAcknowledgementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotSubmitMasterdataAcknowledgementsException(Throwable cause) {
        super(cause);
    }
}
