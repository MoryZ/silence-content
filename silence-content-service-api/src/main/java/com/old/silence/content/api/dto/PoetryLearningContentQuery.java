package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * PoetryLearningContent查询对象
 */
public class PoetryLearningContentQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String title;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String subtitle;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long contentType;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger gradeId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger categoryId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger subCategoryId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long difficulty;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String author;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String dynasty;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String background;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String usageExamples;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String annotations;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String audioUrl;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String imageUrl;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long viewCount;


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

    public BigInteger getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(BigInteger subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
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

}