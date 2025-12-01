package com.old.silence.content.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.data.commons.domain.AbstractAuditable;
import com.old.silence.data.commons.domain.ExplictNewPersistable;

import java.math.BigInteger;

/**
 * @author moryzang
 */

@Entity
public class ContentArticle extends AbstractAuditable<BigInteger>
        implements ExplictNewPersistable<BigInteger>, ContentAccessor {
    private static final long serialVersionUID = -8556205980180706065L;

    private String reprintDeclaration;

    private String summary;


    @OneToOne
    @JoinColumn(name = "id")
    private Content content;

    @Transient
    @JsonIgnore
    private boolean newEntity = false;

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


    public boolean isNewEntity() {
        return newEntity;
    }

    public void setNewEntity(boolean newEntity) {
        this.newEntity = newEntity;
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
