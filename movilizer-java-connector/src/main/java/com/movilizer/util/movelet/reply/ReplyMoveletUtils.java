package com.movilizer.util.movelet.reply;

import com.movilitas.movilizer.v12.MovilizerReplyAnswer;
import com.movilitas.movilizer.v12.MovilizerReplyMovelet;
import com.movilitas.movilizer.v12.MovilizerReplyQuestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReplyMoveletUtils {
    public static IReplyAnswerMap collectAnswers(MovilizerReplyMovelet replyMovelet) {
        return collectAnswers(replyMovelet, "");
    }

    public static IReplyAnswerMap collectAnswers(MovilizerReplyMovelet replyMovelet, String answerPrefix) {
        Map<String, MovilizerReplyAnswer> map = new HashMap<String, MovilizerReplyAnswer>();
        collectAnswers(replyMovelet, answerPrefix, map);
        return new ReplyAnswerMap(map);
    }

    private static void collectAnswers(MovilizerReplyMovelet replyMovelet, String answerPrefix, Map<String, MovilizerReplyAnswer> replyAnswerMap) {
        List<MovilizerReplyQuestion> replyQuestionList = replyMovelet.getReplyQuestion();
        for (MovilizerReplyQuestion replyQuestion : replyQuestionList) {
            collectAnswers(replyQuestion, answerPrefix, replyAnswerMap);
        }
    }

    private static void collectAnswers(MovilizerReplyQuestion replyQuestion, String answerPrefix, Map<String, MovilizerReplyAnswer> replyAnswerMap) {
        List<MovilizerReplyAnswer> replyAnswerList = replyQuestion.getReplyAnswer();
        for (MovilizerReplyAnswer replyAnswer : replyAnswerList) {
            String answerKey = replyAnswer.getAnswerKey();
            if (answerKey.startsWith(answerPrefix)) {
                replyAnswerMap.put(answerKey, replyAnswer);
            }
        }
    }

    // TODO: unit-test!
    public static Map<String, Map<String, MovilizerReplyAnswer>> collectAnswersByClientKey(MovilizerReplyMovelet replyMovelet, String questionPrefix, String answerPrefix)
    {
        Map<String, Map<String, MovilizerReplyAnswer>> replyAnswersByClientKeyMap = new HashMap<String, Map<String, MovilizerReplyAnswer>>();

        final List<MovilizerReplyQuestion> replyQuestions = replyMovelet.getReplyQuestion();
        for (MovilizerReplyQuestion replyQuestion : replyQuestions) {
            final String questionKey = replyQuestion.getQuestionKey();
            if (questionKey == null || !questionKey.equals(questionPrefix)) {
                continue;
            }
            List<MovilizerReplyAnswer> replyAnswers = replyQuestion.getReplyAnswer();
            for (MovilizerReplyAnswer replyAnswer : replyAnswers) {
                String answerKey = replyAnswer.getAnswerKey();
                String clientKey = replyAnswer.getClientKey();
                if (!answerKey.startsWith(answerPrefix)) {
                    continue;
                }
                if (replyAnswersByClientKeyMap.containsKey(clientKey)) {
                    Map<String, MovilizerReplyAnswer> replyAnswerMap = replyAnswersByClientKeyMap.get(clientKey);
                    replyAnswerMap.put(answerKey, replyAnswer);
                } else {
                    Map<String, MovilizerReplyAnswer> replyAnswerMap = new HashMap<String, MovilizerReplyAnswer>();
                    replyAnswerMap.put(answerKey, replyAnswer);
                    replyAnswersByClientKeyMap.put(clientKey, replyAnswerMap);
                }
            }
        }

        return replyAnswersByClientKeyMap;
    }
}
