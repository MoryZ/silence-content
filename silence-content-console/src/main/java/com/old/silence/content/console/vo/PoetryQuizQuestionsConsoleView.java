package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.QuestionType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* PoetryQuizQuestions视图接口
*/
public interface PoetryQuizQuestionsConsoleView extends AuditableView {
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