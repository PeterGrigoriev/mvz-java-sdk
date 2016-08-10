package com.movilizer.util.movelet.reply;

import com.movilitas.movilizer.v15.MovilizerReplyAnswer;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static com.movilizer.util.encoding.EncodingUtils.byteArrayToBase64;
import static java.lang.Integer.parseInt;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class ReplyAnswerMap implements IReplyAnswerMap {
    private final Map<String, MovilizerReplyAnswer> map;

    public ReplyAnswerMap(Map<String, MovilizerReplyAnswer> map) {
        this.map = map;
    }

    @Override
    public String getRequiredString(String key) throws ReplyAnswerValueCannotBeRetrievedException {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            throw new ReplyAnswerNotPresentException(key);
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            throw new ReplyAnswerValueIsNullException(key);
        }
        return value;
    }

    @Override
    public String getOptionalString(String key, String defaultValue) {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            return defaultValue;
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public String getOptionalString(String key) {
        return getOptionalString(key, "");
    }

    @Override
    public int getRequiredInt(String key) throws ReplyAnswerValueCannotBeRetrievedException {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            throw new ReplyAnswerNotPresentException(key);
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            throw new ReplyAnswerValueIsNullException(key);
        }
        try {
            return parseInt(value);
        } catch (NumberFormatException cause) {
            throw new ReplyAnswerValueNotParseableException(key, value, cause);
        }
    }

    @Override
    public int getOptionalInt(String key, int defaultValue) {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            return defaultValue;
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            return defaultValue;
        }
        try {
            return parseInt(value);
        } catch (NumberFormatException cause) {
            return defaultValue;
        }
    }

    @Override
    public Integer getOptionalInteger(String key, Integer defaultValue) {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            return defaultValue;
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            return defaultValue;
        }
        try {
            return parseInt(value);
        } catch (NumberFormatException cause) {
            return defaultValue;
        }
    }

    @Override
    public float getRequiredFloat(String key) throws ReplyAnswerValueCannotBeRetrievedException {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            throw new ReplyAnswerNotPresentException(key);
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            throw new ReplyAnswerValueIsNullException(key);
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException cause) {
            throw new ReplyAnswerValueNotParseableException(key, value, cause);
        }
    }

    @Override
    public float getOptionalFloat(String key, float defaultValue) {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            return defaultValue;
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException cause) {
            return defaultValue;
        }
    }

    @Override
    public Float getOptionalFloat(String key) {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            return null;
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            return null;
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException cause) {
            return null;
        }
    }

    @Override
    public Date getRequiredDate(String key) throws ReplyAnswerValueCannotBeRetrievedException {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            throw new ReplyAnswerNotPresentException(key);
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            throw new ReplyAnswerValueIsNullException(key);
        }
        try {
            return new Date(Long.parseLong(value));
        } catch (NumberFormatException cause) {
            throw new ReplyAnswerValueNotParseableException(key, value, cause);
        }
    }

    @Override
    public Date getOptionalDate(String key, Date defaultValue) {
        MovilizerReplyAnswer replyAnswer = map.get(key);
        if (replyAnswer == null) {
            return defaultValue;
        }
        String value = replyAnswer.getValue();
        if (value == null) {
            return defaultValue;
        }
        try {
            return new Date(Long.parseLong(value));
        } catch (NumberFormatException cause) {
            return defaultValue;
        }
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public MovilizerReplyAnswer get(String key) {
        return map.get(key);
    }

    @Override
    public String getBase64Image(String key) {
        MovilizerReplyAnswer movilizerReplyAnswer = get(key);
        if(null == movilizerReplyAnswer) {
            return null;
        }
        byte[] data = movilizerReplyAnswer.getData();
        if(null == data)  {
            return null;
        }
        return byteArrayToBase64(data);
    }
}
