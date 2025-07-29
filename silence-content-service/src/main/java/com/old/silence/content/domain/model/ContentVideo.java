package com.old.silence.content.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.data.commons.domain.AbstractAuditable;
import com.old.silence.data.commons.domain.ExplictNewPersistable;

/**
 * @author MurrayZhang
 */

@Entity
public class ContentVideo extends AbstractAuditable<BigInteger>
implements ExplictNewPersistable<BigInteger>, ContentAccessor {
    private static final long serialVersionUID = -4330333820040633662L;

    private String description;

    private Integer duration;

    private Integer width;

    private Integer height;

    @OneToOne
    @JoinColumn(name = "id")
    private Content content;

    @Transient
    @JsonIgnore
    private boolean newEntity = false;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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
