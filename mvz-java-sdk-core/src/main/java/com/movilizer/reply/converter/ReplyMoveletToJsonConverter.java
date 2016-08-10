package com.movilizer.reply.converter;

import com.google.common.base.Function;
import com.google.gson.JsonObject;
import com.movilitas.movilizer.v15.MovilizerReplyMovelet;
import com.movilitas.movilizer.v15.MovilizerReplyQuestion;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ReplyMoveletToJsonConverter implements Function<MovilizerReplyMovelet, JsonObject> {

    @Nullable
    @Override
    public JsonObject apply(MovilizerReplyMovelet movilizerReplyMovelet) {
        JsonObject jsonObject  = new JsonObject();

        List<MovilizerReplyQuestion> questions = movilizerReplyMovelet.getReplyQuestion();
        for (MovilizerReplyQuestion question : questions) {
            addAnswerValues(jsonObject, question);
        }


        return jsonObject;
    }

    private void addAnswerValues(JsonObject jsonObject, MovilizerReplyQuestion question) {
        Map<String, Answer> answers = Answer.ofQuestion(question);

        Set<String> answerKeys = answers.keySet();

        for (String answerKey : answerKeys) {
            Answer answer = answers.get(answerKey);
            if(answer.getType() != AnswerType.VOID) {
                jsonObject.add(answer.getKey(), answer.toJsonElement());
            }
        }
    }

}
