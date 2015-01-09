package com.movilizer.util.movelet.reply;

import com.movilitas.movilizer.v11.MovilizerReplyMovelet;
import com.movilizer.mockdata.saved.SavedMovilizerObjectLoader;
import org.testng.annotations.Test;

import static com.movilizer.util.movelet.reply.ReplyMoveletUtils.collectAnswers;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author philippe.guillamet@gdfsuez.com
 */
public class ReplyMoveletUtilsTest {
    @Test
    public void testCollectAnswers() throws Exception {
        MovilizerReplyMovelet replyMovelet = SavedMovilizerObjectLoader.getInstance().loadMovilizerReplyMovelet("ReplyMovelet.xml");
        assertNotNull(replyMovelet);
        IReplyAnswerMap replyAnswerMap = collectAnswers(replyMovelet);
        assertNotNull(replyAnswerMap);
        assertTrue(replyAnswerMap.keySet().size() == 2);
    }

    @Test
    public void testCollectAnswersWithPrefix() throws Exception {
        MovilizerReplyMovelet replyMovelet = SavedMovilizerObjectLoader.getInstance().loadMovilizerReplyMovelet("ReplyMovelet.xml");
        assertNotNull(replyMovelet);

        IReplyAnswerMap replyAnswerMap = collectAnswers(replyMovelet, "A_REQUIRED");
        assertNotNull(replyAnswerMap);
        assertTrue(replyAnswerMap.keySet().size() == 2);

        replyAnswerMap = collectAnswers(replyMovelet, "A_REQUIRED_STRING");
        assertTrue(replyAnswerMap.keySet().size() == 1);
    }

}
