package com.movilizer.reply.converter;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum AnswerSource {
    VALUE, CLIENT_KEY, DATA, VOID;

    public static AnswerSource fromString(String s) {
        if("clientKey".equals(s)) {
            return CLIENT_KEY;
        }
        if("data".equals(s)) {
            return DATA;
        }
        return VALUE;
    }
}
