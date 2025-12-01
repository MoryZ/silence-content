package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.old.silence.validation.group.UpdateValidation;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class ContentArticleConsoleCommand extends ContentConsoleCommand {

    @NotNull(groups = UpdateValidation.class)
    private BigInteger id;

    @NotBlank
    @Size(max = 200)
    private String reprintDeclaration;

    @Size(max = 200)
    private String summary;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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
