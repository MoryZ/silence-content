package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * @author moryzang
 */
public class ContentArticleCommand extends ContentCommand {

    @NotBlank
    @Size(max = 200)
    private String reprintDeclaration;

    @Size(max = 200)
    private String summary;

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


}
