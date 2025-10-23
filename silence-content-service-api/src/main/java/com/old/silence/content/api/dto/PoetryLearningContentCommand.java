package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
/**
* PoetryLearningContent命令对象
*/
public class PoetryLearningContentCommand {
    @NotBlank
    @Size(max = 200)
    private String title;
    private String subtitle;
    @NotNull
    private Long contentType;
    @NotNull
    private BigInteger gradeId;
    @NotNull
    private BigInteger categoryId;
    @NotNull
    private BigInteger subcategoryId;
    @NotNull
    private Long difficulty;
    private String originalText;
    private String author;
    private String dynasty;
    private String background;
    private String explanation;
    private String usageExamples;
    private String annotations;
    private String translation;
    private String appreciation;
    private String audioUrl;
    private String imageUrl;
    private Long viewCount;
    private String isEnabled;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public Long getContentType() {
        return this.contentType;
    }

    public void setContentType(Long contentType) {
        this.contentType = contentType;
    }
    public BigInteger getGradeId() {
        return this.gradeId;
    }

    public void setGradeId(BigInteger gradeId) {
        this.gradeId = gradeId;
    }
    public BigInteger getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }
    public BigInteger getSubcategoryId() {
        return this.subcategoryId;
    }

    public void setSubcategoryId(BigInteger subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
    public Long getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }
    public String getOriginalText() {
        return this.originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDynasty() {
        return this.dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }
    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
    public String getExplanation() {
        return this.explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public String getUsageExamples() {
        return this.usageExamples;
    }

    public void setUsageExamples(String usageExamples) {
        this.usageExamples = usageExamples;
    }
    public String getAnnotations() {
        return this.annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }
    public String getTranslation() {
        return this.translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
    public String getAppreciation() {
        return this.appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }
    public String getAudioUrl() {
        return this.audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Long getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
    public String getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}