package com.movilizer.util.movelet.reply;

import com.movilitas.movilizer.v12.MovilizerReplyAnswer;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class ReplyAnswerMapTest {
    private final ReplyAnswerMap replyAnswerMap;
    private final static String KEY_REQUIRED_STRING_WITH_VALUE = "A_Required_String_With_Value";
    private final static String VALUE_REQUIRED_STRING_WITH_VALUE = "required string value";
    private final static String KEY_REQUIRED_STRING_WITHOUT_VALUE = "A_Required_String_Without_Value";
    private final static String KEY_NOT_PRESENT = "A_Not_Present";

    public ReplyAnswerMapTest() {
        Map<String, MovilizerReplyAnswer> map = buildMap();
        replyAnswerMap = new ReplyAnswerMap(map);
    }

    private Map<String, MovilizerReplyAnswer> buildMap() {
        Map<String, MovilizerReplyAnswer> map = new HashMap<String, MovilizerReplyAnswer>();

        addReplyAnswer(KEY_REQUIRED_STRING_WITH_VALUE, VALUE_REQUIRED_STRING_WITH_VALUE, map);
        addReplyAnswer(KEY_REQUIRED_STRING_WITHOUT_VALUE, null, map);

        return map;
    }

    private void addReplyAnswer(String key, String value, Map<String, MovilizerReplyAnswer> map) {
        MovilizerReplyAnswer replyAnswer = new MovilizerReplyAnswer();
        replyAnswer.setAnswerKey(key);
        replyAnswer.setValue(value);
        map.put(key, replyAnswer);
    }

    @Test
    public void testGetRequiredString() throws Exception {
        String value = replyAnswerMap.getRequiredString(KEY_REQUIRED_STRING_WITH_VALUE);
        assertNotNull(value);
        assertTrue(VALUE_REQUIRED_STRING_WITH_VALUE.equals(value));
    }

    @Test(expectedExceptions = ReplyAnswerValueCannotBeRetrievedException.class)
    public void testGetRequiredStringThrowException() throws Exception {
        assertNotNull(replyAnswerMap.getRequiredString(KEY_NOT_PRESENT));
    }
}
