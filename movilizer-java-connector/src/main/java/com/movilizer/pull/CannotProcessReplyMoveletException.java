package com.movilizer.pull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CannotProcessReplyMoveletException extends Exception {
    public CannotProcessReplyMoveletException(String message) {
        super(message);

    }

    public CannotProcessReplyMoveletException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotProcessReplyMoveletException(Throwable cause) {
        super(cause);
    }
}
