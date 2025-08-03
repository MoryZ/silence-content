package com.old.silence.content.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.math.BigInteger;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.data.commons.domain.AbstractAuditable;
import com.old.silence.data.commons.domain.ExplictNewPersistable;

/**
 * @author moryzang
 */

@Entity
public class ContentProductTerm extends AbstractAuditable<BigInteger>
implements ExplictNewPersistable<BigInteger>, ContentAccessor {
    private static final long serialVersionUID = -4330333820040633662L;

    @Column(updatable = false)
    private String productCode;

    private Instant onSaleAt;

    private Instant offSaleAt;

    private Long displayOrder;

    @OneToOne
    @JoinColumn(name = "id")
    private Content content;

    @Transient
    @JsonIgnore
    private boolean newEntity = false;


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

    @Override
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    @Transient
    @JsonIgnore
    public boolean isNew() {
        return newEntity;
    }

    @Override
    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }

}
