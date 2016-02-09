package com.movilizer.util.movelet.reply;

import com.movilitas.movilizer.v14.MovilizerReplyAnswer;

import java.util.Date;
import java.util.Set;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public interface IReplyAnswerMap {
    String getRequiredString(String key) throws ReplyAnswerValueCannotBeRetrievedException;

    String getOptionalString(String key, String defaultValue);

    /**
     *
     * @param key - answer key to retrieve value for
     * @return Answer value as Sting, or empty string, if the key is not found,
     */
    String getOptionalString(String key);

    int getRequiredInt(String key) throws ReplyAnswerValueCannotBeRetrievedException;

    int getOptionalInt(String key, int defaultValue);

    Integer getOptionalInteger(String key, Integer defaultValue);

    float getRequiredFloat(String key) throws ReplyAnswerValueCannotBeRetrievedException;

    float getOptionalFloat(String key, float defaultValue);

    Float getOptionalFloat(String key);

    Date getRequiredDate(String key) throws ReplyAnswerValueCannotBeRetrievedException;

    Date getOptionalDate(String key, Date defaultValue);

    boolean containsKey(String key);

    Set<String> keySet();

    MovilizerReplyAnswer get(String key);

    String getBase64Image(String key);
}
