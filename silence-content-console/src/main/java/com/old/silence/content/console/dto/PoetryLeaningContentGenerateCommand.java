package com.old.silence.content.console.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public class PoetryLeaningContentGenerateCommand {


    private List<BigInteger> subCategoryIds;

    @NotNull
    private BigInteger poetryGradeId;

    public List<BigInteger> getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(List<BigInteger> subCategoryIds) {
        this.subCategoryIds = subCategoryIds;
    }

    public BigInteger getPoetryGradeId() {
        return poetryGradeId;
    }

    public void setPoetryGradeId(BigInteger poetryGradeId) {
        this.poetryGradeId = poetryGradeId;
    }
}
