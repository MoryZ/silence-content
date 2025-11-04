package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

import com.old.silence.content.domain.enums.QuestionType;

/**
* PoetryQuizQuestions命令对象
*/
public class PoetryQuizQuestionsCommand {
    @NotNull
    private BigInteger contentId;
    @NotNull
    private QuestionType questionType;
    @NotBlank
    @Size(max = 65535)
    private String questionStem;
    private String questionData;
    @NotBlank
    @Size(max = 0)
    private String correctAnswer;
    private String explanation;
    private Long difficulty;
    private String hints;
    private Boolean enable;

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
    public String getQuestionStem() {
        return this.questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }
    public String getQuestionData() {
        return this.questionData;
    }

    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public String getExplanation() {
        return this.explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public Long getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }
    public String getHints() {
        return this.hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }
    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}