package com.movilizer.pull;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public class CannotProcessMasterdataAcknowledgementException extends Exception {
    public CannotProcessMasterdataAcknowledgementException(String message) {
        super(message);

    }

    public CannotProcessMasterdataAcknowledgementException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotProcessMasterdataAcknowledgementException(Throwable cause) {
        super(cause);
    }
}
