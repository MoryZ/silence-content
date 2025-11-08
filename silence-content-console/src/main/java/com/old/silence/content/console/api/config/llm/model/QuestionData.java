package com.old.silence.content.infrastructure.llm.model;

import java.util.List;
import java.util.Map;

/**
 * @author moryzang
 */
public class QuestionData {
    public String questionType;
    public String questionStem;
    public Map<String, Object> questionData;
    public Object correctAnswer;  // 改为 Object，支持对象或数组
    public String explanation;
    public Integer difficulty;
    public List<String> hints;

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionStem() {
        return questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    public Map<String, Object> getQuestionData() {
        return questionData;
    }

    public void setQuestionData(Map<String, Object> questionData) {
        this.questionData = questionData;
    }

    public Object getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Object correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }
}
