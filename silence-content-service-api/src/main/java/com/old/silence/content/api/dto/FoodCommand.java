package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author moryzang
 * @Description
 */
public class FoodCommand {
    @NotNull
    private BigInteger categoryId;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Integer status;

    public @NotNull BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NotNull BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    public @NotBlank String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotBlank String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }
}
