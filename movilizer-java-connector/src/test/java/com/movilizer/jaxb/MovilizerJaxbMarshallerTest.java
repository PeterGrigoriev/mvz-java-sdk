package com.movilizer.jaxb;

import com.movilitas.movilizer.v14.MovilizerReplyAnswer;
import com.movilitas.movilizer.v14.MovilizerReplyMovelet;
import com.movilitas.movilizer.v14.MovilizerReplyQuestion;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerJaxbMarshallerTest {
    private MovilizerJaxbMarshaller marshaller;

    public MovilizerJaxbMarshallerTest() {
        this.marshaller = MovilizerJaxbMarshaller.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(marshaller);
    }

    @Test
    public void testMarshallReplyQuestion() throws Exception {
        MovilizerReplyQuestion question = new MovilizerReplyQuestion();
        question.setQuestionKey("Q1");
        String xml = marshaller.marshall(question);
        assertNotNull(xml);
        assertTrue(xml.contains("Q1"));
    }

    @Test void testMarshallReplyMovelet() throws Exception {
        MovilizerReplyMovelet replyMovelet = new MovilizerReplyMovelet();
        replyMovelet.setMoveletKey("TEST_MOVELET_KEY");


        MovilizerReplyQuestion replyQuestion = new MovilizerReplyQuestion();

        replyQuestion.setQuestionKey("Q1");
        replyQuestion.setNextQuestionKey("NEXT_KEY");
        replyQuestion.setPreviousQuestionKey("PREV_KEY");
        replyQuestion.setQuestionCountAbsolute(1);
        replyQuestion.setQuestionCountNormalized(1);


        MovilizerReplyAnswer movilizerReplyAnswer = new MovilizerReplyAnswer();
        movilizerReplyAnswer.setAnswerKey("Q1_A1");
        movilizerReplyAnswer.setAttributeType((byte) 0);
        movilizerReplyAnswer.setPosition((short) 1);
        movilizerReplyAnswer.setValue("Test Answer Value");
        replyQuestion.getReplyAnswer().add(movilizerReplyAnswer);
        replyMovelet.getReplyQuestion().add(replyQuestion);

        String xml = MovilizerJaxbMarshaller.getInstance().marshall(replyMovelet);
        assertNotNull(xml);
        assertFalse(xml.isEmpty());
    }


}
