package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
/**
* PoetryUserFavorite命令对象
*/
public class PoetryUserFavoriteCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private BigInteger contentId;

    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
    public BigInteger getContentId() {
        return this.contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }
}