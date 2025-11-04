package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.QuestionType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* PoetryQuizQuestions视图接口
*/
@ProjectedPayload
public interface PoetryQuizQuestionsView extends AuditableView {
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