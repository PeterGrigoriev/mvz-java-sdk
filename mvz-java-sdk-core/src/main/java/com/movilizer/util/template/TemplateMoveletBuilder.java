package com.movilizer.util.template;

import com.movilitas.movilizer.v14.MovilizerAnswer;
import com.movilitas.movilizer.v14.MovilizerMovelet;
import com.movilitas.movilizer.v14.MovilizerQuestion;
import com.movilizer.moveletbuilder.*;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.ArrayList;
import java.util.List;

import static com.movilizer.util.datetime.ValidityDateUtils.setValidTillDate;
import static com.movilizer.util.string.StringUtils.isNullOrEmpty;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class TemplateMoveletBuilder implements IMoveletBuilder {
    protected final IMoveletDataProvider moveletDataProvider;
    protected final String[] templateNames;
    protected final ITemplateRepository repository;
    protected final List<IXmlTemplate> moveletTemplates;

    public TemplateMoveletBuilder(IMoveletDataProvider moveletDataProvider, ITemplateRepository repository, String... templateNames) {
        this.moveletDataProvider = moveletDataProvider;
        this.templateNames = templateNames;
        this.repository = repository;
        moveletTemplates = getTemplates(repository, this.templateNames);
    }


    private static final ILogger logger = ComponentLogger.getInstance("TemplateMoveletBuilder");

    private List<IXmlTemplate> getTemplates(ITemplateRepository repository, String[] templateNames) {
        List<IXmlTemplate> templateList = new ArrayList<IXmlTemplate>();
        for (String templateName : templateNames) {
            IXmlTemplate template = getTemplate(repository, templateName);
            if (template == null) {
                logger.error("Cannot load template: " + templateName);
                return new ArrayList<IXmlTemplate>();
            }
            templateList.add(template);
        }
        return templateList;
    }


    private static IXmlTemplate getTemplate(ITemplateRepository repository, String templateName) {
        try {
            return repository.getTemplate(templateName);
        } catch (Exception e) {
            logger.error("Cannot get template [" + templateName + "] using the repository [" + repository + "]", e);
            return null;
        }
    }

    @Override
    public List<MovilizerMovelet> buildMovelets() {
        List<MovilizerMovelet> movelets = new ArrayList<MovilizerMovelet>();
        for (IXmlTemplate moveletTemplate : moveletTemplates) {
            String moveletXml = moveletTemplate.produceXml(moveletDataProvider);
            XmlMoveletBuilder xmlMoveletBuilder = new XmlMoveletBuilder(moveletXml);
            movelets.addAll(xmlMoveletBuilder.buildMovelets());
        }

        for (MovilizerMovelet movelet : movelets) {
            // movelet.setMoveletKeyExtension(moveletDataProvider.getMoveletKeyExtension());
            postProcessMovelet(movelet);
        }

        return movelets;
    }

    private void postProcessMovelet(MovilizerMovelet movelet) {
        addDefaultValidTillDate(movelet);
        addMissingQuestionKeys(movelet);
        addMissingNextQuestionKeys(movelet);
        addMissingAnswerKeys(movelet);
        addMissingInitialQuestionKey(movelet);
    }

    public void addMissingQuestionKeys(MovilizerMovelet movelet) {
        List<MovilizerQuestion> questions = movelet.getQuestion();
        for (int i = 0; i < questions.size(); i++) {
            MovilizerQuestion question = questions.get(i);
            if (isNullOrEmpty(question.getKey())) {
                String key = "Q" + (i + 1);
                logger.debug("Automatically setting missing question key [" + key + "]");
                question.setKey(key);
            }
        }
    }

    public void addMissingInitialQuestionKey(MovilizerMovelet movelet) {
        if (isNullOrEmpty(movelet.getInitialQuestionKey())) {
            List<MovilizerQuestion> questions = movelet.getQuestion();
            if (!questions.isEmpty()) {
                String initialQuestionKey = questions.get(0).getKey();
                logger.debug("Automatically setting initial question key to [" + initialQuestionKey + "]");
                movelet.setInitialQuestionKey(initialQuestionKey);
            }
        }
    }

    public static void addMissingNextQuestionKeys(MovilizerMovelet movelet) {
        List<MovilizerQuestion> questions = movelet.getQuestion();
        int numberOfQuestions = questions.size();

        for (int i = 0; i < numberOfQuestions; i++) {
            MovilizerQuestion movilizerQuestion = questions.get(i);
            String nextQuestionKey = i == numberOfQuestions - 1 ? "END" : questions.get(i + 1).getKey();
            setMissingNextQuestionKey(movilizerQuestion.getAnswer(), nextQuestionKey);
        }
    }

    public static void setMissingNextQuestionKey(List<MovilizerAnswer> answers, String nextQuestionKey) {
        for (MovilizerAnswer answer : answers) {
            if (isNullOrEmpty(answer.getNextQuestionKey())) {
                logger.debug("Automatically setting the next question key [" + nextQuestionKey + "] for answer " + answer.getKey());
                answer.setNextQuestionKey(nextQuestionKey);
            }
        }
    }

    public static void addMissingAnswerKeys(MovilizerMovelet movelet) {
        List<MovilizerQuestion> questions = movelet.getQuestion();

        for (int i = 0; i < questions.size(); i++) {
            MovilizerQuestion question = questions.get(i);
            String questionKey = question.getKey();
            if(isNullOrEmpty(questionKey)) {
                questionKey = "Q" + (i + 1);
            }
            List<MovilizerAnswer> answers = question.getAnswer();
            for (int answerPosition = 0; answerPosition < answers.size(); answerPosition++) {
                MovilizerAnswer answer = answers.get(answerPosition);
                addMissingAnswerKey(answer, questionKey, answerPosition);
            }
        }
    }

    private static void addMissingAnswerKey(MovilizerAnswer answer, String questionKey, int answerPosition) {
        if(isNullOrEmpty(answer.getKey())) {
            String key = "_" + questionKey + "_A_" + answerPosition;
            logger.debug("Automatically setting answer key [" + key + "]");

            answer.setKey(key);
        }
    }

    private void addDefaultValidTillDate(MovilizerMovelet movelet) {
        logger.trace(String.format("Setting validity date: %s|%s->%s", movelet.getMoveletKey(), movelet.getMoveletKeyExtension(), moveletDataProvider.getValidTillDate()));
        setValidTillDate(movelet, moveletDataProvider.getValidTillDate());
    }

    @Override
    public void onRequestAction(RequestEvent action) {
        if (moveletDataProvider instanceof IMoveletPushListener) {
            ((IMoveletPushListener) moveletDataProvider).onRequestAction(action);
        }
    }
}
