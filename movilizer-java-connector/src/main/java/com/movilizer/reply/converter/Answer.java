package com.movilizer.reply.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.movilitas.movilizer.v14.MovilizerReplyAnswer;
import com.movilitas.movilizer.v14.MovilizerReplyQuestion;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.movilizer.reply.converter.BooleanHelper.toBoolean;
import static com.movilizer.util.encoding.EncodingUtils.byteArrayToBase64;
import static org.apache.commons.lang.math.NumberUtils.toInt;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class Answer {
    private final AnswerKey answerKey;
    private final List<MovilizerReplyAnswer> answers;

    public Answer(AnswerKey answerKey, List<MovilizerReplyAnswer> answers) {
        this.answerKey = answerKey;
        this.answers = answers;
    }

    public static Map<String, Answer> ofQuestion(MovilizerReplyQuestion question) {
        if (question == null) {
            return newHashMap();
        }
        Map<String, Answer> result = newHashMap();
        Map<String, List<MovilizerReplyAnswer>> replyAnswers = collectReplyAnswers(question.getReplyAnswer());
        Set<String> keys = replyAnswers.keySet();
        for (String key : keys) {
            AnswerKey answerKey = AnswerKey.fromString(key);
            if (AnswerType.VOID != answerKey.getType()) {
                result.put(answerKey.getKey(), new Answer(answerKey, replyAnswers.get(key)));
            }
        }
        return result;
    }


    private static Map<String, List<MovilizerReplyAnswer>> collectReplyAnswers(List<MovilizerReplyAnswer> answers) {
        Map<String, List<MovilizerReplyAnswer>> map = newHashMap();
        for (MovilizerReplyAnswer answer : answers) {
            String key = answer.getAnswerKey();
            List<MovilizerReplyAnswer> answersWithThisKey = map.get(key);
            if (answersWithThisKey == null) {
                answersWithThisKey = newArrayList();
                map.put(key, answersWithThisKey);
            }
            answersWithThisKey.add(answer);
        }
        return map;
    }


    public AnswerType getType() {
        return answerKey.getType();
    }

    public String getKey() {
        return answerKey.getKey();
    }

    public String getStringValue() {
        return getStringValue(answers.get(0));
    }

    public String getBinaryValue() {
        MovilizerReplyAnswer movilizerReplyAnswer = answers.get(0);
        return getBinaryValue(movilizerReplyAnswer);
    }

    private String getBinaryValue(MovilizerReplyAnswer movilizerReplyAnswer) {
        return byteArrayToBase64(movilizerReplyAnswer.getData());
    }

    public int getIntValue() {
        return toInt(getStringValue(), 0);
    }

    public boolean getBooleanValue() {
        return toBoolean(getStringValue());
    }

    public JsonElement toJsonElement() {
        switch (getType()) {
            case INT:
                return new JsonPrimitive(getIntValue());
            case STRING:
                return new JsonPrimitive(getStringValue());
            case BINARY:
                return new JsonPrimitive(getBinaryValue());
            case BOOLEAN:
                return new JsonPrimitive(getBooleanValue());

            case ARRAY_STRING:
                return toJsonStringArray(getStringArrayValue());
            case ARRAY_INT:
                return toJsonIntArray(getIntArrayValue());

            case ARRAY_BINARY:
                return toJsonStringArray(getBinaryArrayValue());
        }
        return JsonNull.INSTANCE;
    }

    private JsonArray toJsonIntArray(List<Integer> intArrayValues) {
        JsonArray array = new JsonArray();
        for (Integer value : intArrayValues) {
            array.add(new JsonPrimitive(value));
        }
        return array;
    }


    private static JsonArray toJsonStringArray(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(new JsonPrimitive(value));
        }
        return array;
    }

    public List<String> getStringArrayValue() {
        List<String> list = newArrayList();
        for (MovilizerReplyAnswer answer : answers) {
            list.add(getStringValue(answer));
        }
        return list;
    }

    public List<Integer> getIntArrayValue() {
        List<Integer> list = newArrayList();
        for (MovilizerReplyAnswer answer : answers) {
            list.add(getIntValue(answer));
        }
        return list;
    }


    public List<String> getBinaryArrayValue() {
        List<String> list = newArrayList();
        for (MovilizerReplyAnswer answer : answers) {
            list.add(getBinaryValue(answer));
        }
        return list;
    }

    public Integer getIntValue(MovilizerReplyAnswer answer) {
        return toInt(getStringValue(answer), 0);
    }

    private String getStringValue(MovilizerReplyAnswer answer) {
        AnswerSource source = answerKey.getSource();

        if (source == AnswerSource.CLIENT_KEY) {
            return answer.getClientKey();
        }
        if (source == AnswerSource.DATA) {
            return getBinaryValue(answer);
        }
        return answer.getValue();
    }
}
