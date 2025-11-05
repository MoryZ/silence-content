package com.old.silence.content.application.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.QuestionType;
import com.old.silence.data.commons.domain.AuditableView;

/**
* PoetryQuizQuestions视图接口
*/
@ProjectedPayload
public interface PoetryQuizQuestionsApplicationView extends AuditableView {
    BigInteger getId();

    BigInteger getContentId();

    QuestionType getQuestionType();

    String getQuestionStem();

    String getQuestionData();

    String getCorrectAnswer();

    String getExplanation();

    Long getDifficulty();

    String getHints();

    Boolean getEnable();


}