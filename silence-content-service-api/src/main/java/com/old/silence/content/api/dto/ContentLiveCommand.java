package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.content.domain.enums.LiveStatus;
import com.old.silence.content.domain.enums.TabularImageReferenceMode;
import com.old.silence.validation.group.UpdateValidation;

/**
 * @author moryzang
 */
public class ContentLiveCommand extends ContentCommand{


    @NotBlank
    @Size(max = 200)
    private String roomId;

    private Instant startDte;

    private Instant finishDate;

    private Instant activeDate;

    private Instant endDte;

    @NotNull
    private LiveStatus liveStatus;

    private String tabularImageReference;

    private TabularImageReferenceMode tabularImageReferenceMode;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Instant getStartDte() {
        return startDte;
    }

    public void setStartDte(Instant startDte) {
        this.startDte = startDte;
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

    public Instant getEndDte() {
        return endDte;
    }

    public void setEndDte(Instant endDte) {
        this.endDte = endDte;
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
}
