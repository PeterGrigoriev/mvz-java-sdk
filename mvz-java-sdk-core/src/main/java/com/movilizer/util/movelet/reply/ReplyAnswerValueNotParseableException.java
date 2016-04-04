package com.movilizer.util.movelet.reply;

import static java.lang.String.format;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class ReplyAnswerValueNotParseableException extends ReplyAnswerValueCannotBeRetrievedException {

    public ReplyAnswerValueNotParseableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplyAnswerValueNotParseableException(String replyAnswerKey, String replyAnswerValue, Throwable cause) {
        super(format("Reply answer -> key [%s] ; value [%s]", replyAnswerKey, replyAnswerValue), cause);
    }
}
