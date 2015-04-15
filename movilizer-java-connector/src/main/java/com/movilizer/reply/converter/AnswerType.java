package com.movilizer.reply.converter;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum AnswerType {
    INT, STRING, VOID, BOOLEAN, ARRAY_STRING, ARRAY_INT, BINARY;

    public static AnswerType fromString(String string) {
        if (null == string) {
            return VOID;
        }

        String s = string.toLowerCase();
        if ("string".equals(s)) {
            return STRING;
        }
        if ("int".equals(s)) {
            return INT;
        }
        if ("bool".equals(s) || "boolean".equals(s)) {
            return BOOLEAN;
        }
        if ("array[int]".equals(s)) {
            return ARRAY_INT;
        }
        if ("array[string]".equals(s)) {
            return ARRAY_STRING;
        }
        if ("binary".equals(s)) {
            return BINARY;
        }
        return VOID;
    }
}
