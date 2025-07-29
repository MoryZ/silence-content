package com.old.silence.content.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.math.BigInteger;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.old.silence.content.domain.enums.LiveStatus;
import com.old.silence.content.domain.enums.TabularImageReferenceMode;
import com.old.silence.content.domain.model.support.ContentAccessor;
import com.old.silence.data.commons.domain.AbstractAuditable;
import com.old.silence.data.commons.domain.ExplictNewPersistable;

/**
 * @author MurrayZhang
 */

@Entity
public class ContentLive extends AbstractAuditable<BigInteger>
implements ExplictNewPersistable<BigInteger>, ContentAccessor {
    private static final long serialVersionUID = -4330333820040633662L;

    @Column(updatable = false)
    private String roomId;

    private Instant startDate;

    private Instant finishDate;

    private Instant activeDate;

    private Instant endDate;

    private LiveStatus liveStatus;

    private String tabularImageReference;

    private TabularImageReferenceMode tabularImageReferenceMode;

    @OneToOne
    @JoinColumn(name = "id")
    private Content content;

    @Transient
    @JsonIgnore
    private boolean newEntity = false;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public Instant getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Instant activeDate) {
        this.activeDate = activeDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public LiveStatus getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(LiveStatus liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getTabularImageReference() {
        return tabularImageReference;
    }

    public void setTabularImageReference(String tabularImageReference) {
        this.tabularImageReference = tabularImageReference;
    }

    public TabularImageReferenceMode getTabularImageReferenceMode() {
        return tabularImageReferenceMode;
    }

    public void setTabularImageReferenceMode(TabularImageReferenceMode tabularImageReferenceMode) {
        this.tabularImageReferenceMode = tabularImageReferenceMode;
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
