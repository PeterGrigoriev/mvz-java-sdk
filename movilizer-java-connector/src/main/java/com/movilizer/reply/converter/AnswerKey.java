package com.movilizer.reply.converter;


import org.apache.commons.lang.StringUtils;

import static com.movilizer.reply.converter.AnswerType.BINARY;
import static com.movilizer.reply.converter.AnswerType.VOID;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AnswerKey {

    private final String key;
    private final AnswerType type;
    private final AnswerSource source;

    private AnswerKey(String key, AnswerType type, AnswerSource source) {
        this.key = key;
        this.type = type;
        this.source = source;
    }

    public String getKey() {
        return key;
    }

    public AnswerType getType() {
        return type;
    }



    public static AnswerKey fromString(String string) {
        if(string == null) {
            return null;
        }
        if(!string.contains(":")) {
            return new AnswerKey(string, VOID, AnswerSource.VOID);
        }
        String[] split = StringUtils.split(string, ":");

        if(split.length > 3) {
            return new AnswerKey(string, VOID, AnswerSource.VOID);
        }

        String key = split[0];
        AnswerType type = AnswerType.fromString(split[1]);
        AnswerSource source = type == BINARY ? AnswerSource.DATA : AnswerSource.VALUE;
        if(split.length > 2) {
            source = AnswerSource.fromString(split[2]);
        }

        return new AnswerKey(key, type, source);
    }

    public AnswerSource getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerKey answerKey = (AnswerKey) o;

        return key.equals(answerKey.key) && type == answerKey.type;

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return key;
    }


}
