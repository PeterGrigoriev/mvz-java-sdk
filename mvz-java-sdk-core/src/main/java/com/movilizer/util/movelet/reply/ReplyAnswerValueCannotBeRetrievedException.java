package com.movilizer.util.movelet.reply;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class ReplyAnswerValueCannotBeRetrievedException extends Exception {

    public ReplyAnswerValueCannotBeRetrievedException(String message) {
        super(message);
    }

    public ReplyAnswerValueCannotBeRetrievedException(String message, Throwable cause) {
        super(message, cause);
    }
}
