package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.old.silence.content.domain.enums.AppliedToScenarioType;

/**
 * @author moryzang
 */
public class ContentArticleCommand extends ContentCommand {

    @NotBlank
    @Size(max = 200)
    private String reprintDeclaration;

    @Size(max = 200)
    private String summary;

    @NotNull
    private AppliedToScenarioType appliedToScenarioType;

    private String publisher;

    private String smallImageUrlReference;


    public String getReprintDeclaration() {
        return reprintDeclaration;
    }

    public void setReprintDeclaration(String reprintDeclaration) {
        this.reprintDeclaration = reprintDeclaration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public AppliedToScenarioType getAppliedToScenarioType() {
        return appliedToScenarioType;
    }

    public void setAppliedToScenarioType(AppliedToScenarioType appliedToScenarioType) {
        this.appliedToScenarioType = appliedToScenarioType;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSmallImageUrlReference() {
        return smallImageUrlReference;
    }

    public void setSmallImageUrlReference(String smallImageUrlReference) {
        this.smallImageUrlReference = smallImageUrlReference;
    }
}
