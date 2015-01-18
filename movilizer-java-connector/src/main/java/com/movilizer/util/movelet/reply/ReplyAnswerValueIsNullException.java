package com.movilizer.util.movelet.reply;

import static java.lang.String.format;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class ReplyAnswerValueIsNullException extends ReplyAnswerValueCannotBeRetrievedException {
    public ReplyAnswerValueIsNullException(String replyAnswerKey) {
        super(format("Reply answer -> key [%s]", replyAnswerKey));
    }
}
