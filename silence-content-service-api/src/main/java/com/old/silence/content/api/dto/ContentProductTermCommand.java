package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;


/**
 * @author moryzang
 */
public class ContentProductTermCommand extends ContentCommand{


    @NotBlank
    @Size(max = 45)
    private String productCode;

    private Instant onSaleAt;

    private Instant offSaleAt;


    @NotNull
    @Positive
    private Long displayOrder;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Instant getOnSaleAt() {
        return onSaleAt;
    }

    public void setOnSaleAt(Instant onSaleAt) {
        this.onSaleAt = onSaleAt;
    }

    public Instant getOffSaleAt() {
        return offSaleAt;
    }

    public void setOffSaleAt(Instant offSaleAt) {
        this.offSaleAt = offSaleAt;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
}
