package com.movilizer.usermanagement.json;

import com.google.gson.JsonObject;
import com.movilitas.movilizer.v15.MovilizerReplyAnswer;
import com.movilitas.movilizer.v15.MovilizerReplyMovelet;
import com.movilizer.pull.CannotProcessReplyMoveletException;
import com.movilizer.pull.IReplyMoveletProcessor;
import com.movilizer.util.movelet.reply.IReplyAnswerMap;

import java.util.Set;

import static com.movilizer.util.datetime.DateTimeUtils.asDate;
import static com.movilizer.util.movelet.reply.ReplyMoveletUtils.collectAnswers;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public abstract class JsonReplyMoveletProcessor implements IReplyMoveletProcessor {
    @Override
    public boolean canProcessReplyMovelet(MovilizerReplyMovelet replyMovelet) {
        return true;
    }

    @Override
    public void processReplyMovelet(MovilizerReplyMovelet replyMovelet) throws CannotProcessReplyMoveletException {
        IJsonReply jsonReply = convert(replyMovelet);
        processReply(jsonReply);
    }

    public abstract void processReply(IJsonReply jsonReply);

    public IJsonReply convert(MovilizerReplyMovelet replyMovelet) {
        IReplyAnswerMap replyAnswerMap = collectAnswers(replyMovelet);

        JsonObject data = new JsonObject();
        Set<String> answerKeys = replyAnswerMap.keySet();
        for (String answerKey : answerKeys) {
            if(isAnswerNeeded(answerKey)) {
                MovilizerReplyAnswer replyAnswer = replyAnswerMap.get(answerKey);
                addAnswer(data, answerKey, replyAnswer);
            }
        }
        return new JsonReply(replyMovelet.getMoveletKey(), replyMovelet.getDeviceAddress(), asDate(replyMovelet.getSyncTimestamp()), data);
    }

    private void addAnswer(JsonObject data, String answerKey, MovilizerReplyAnswer replyAnswer) {
        data.addProperty(answerKey, replyAnswer.getValue());
    }

    protected JsonValueType getValueType(@SuppressWarnings("UnusedParameters") MovilizerReplyAnswer movilizerReplyAnswer) {
        return JsonValueType.STRING;
    }




    /**
     * Override to control, which of the answers
     * @param answerKey movelet answer key
     * @return true, if this answer is needed in the resulting Json object
     */
    public boolean isAnswerNeeded(String answerKey) {
        return true;
    }


}
