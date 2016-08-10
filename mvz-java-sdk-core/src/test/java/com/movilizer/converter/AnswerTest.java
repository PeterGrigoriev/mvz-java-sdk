package com.movilizer.converter;

import com.google.gson.JsonArray;
import com.movilitas.movilizer.v14.MovilizerReplyQuestion;
import com.movilizer.jaxb.MovilizerJaxbMarshaller;
import com.movilizer.reply.converter.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static com.movilizer.util.resource.ResourceUtils.fromResource;
import static org.testng.Assert.assertEquals;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AnswerTest {

    private MovilizerReplyQuestion question;

    @BeforeMethod
    public void setUp() throws Exception {
        question = fromResource("/reply-questions/replyQuestion.xml", MovilizerReplyQuestion.class);
    }

    @Test
    public void testOfQuestion() throws Exception {
        Map<String, Answer> answerMap = Answer.ofQuestion(question);
        assertEquals(answerMap.size(), 1);
        JsonArray peopleInvolved = answerMap.get("peopleInvolved").toJsonElement().getAsJsonArray();
        assertEquals(peopleInvolved.size(), 1);
        assertEquals(3, peopleInvolved.get(0).getAsInt());
    }
}