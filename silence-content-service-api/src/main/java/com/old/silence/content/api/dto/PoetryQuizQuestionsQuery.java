package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.QuestionType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * PoetryQuizQuestions查询对象
 */
public class PoetryQuizQuestionsQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger contentId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private QuestionType questionType;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
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