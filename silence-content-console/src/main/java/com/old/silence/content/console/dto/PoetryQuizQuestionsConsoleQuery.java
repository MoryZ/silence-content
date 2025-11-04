package com.old.silence.content.console.dto;


import java.math.BigInteger;

import com.old.silence.content.domain.enums.QuestionType;

/**
* PoetryQuizQuestions查询对象
*/
public class PoetryQuizQuestionsConsoleQuery {
    private BigInteger contentId;
    private QuestionType questionType;
    private Long difficulty;


    public BigInteger getContentId() {
        return this.contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    public Long getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

}