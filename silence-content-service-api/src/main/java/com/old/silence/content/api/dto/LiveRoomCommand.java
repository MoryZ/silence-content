package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;
import com.old.silence.content.domain.enums.LiveRoomType;

/**
* LiveRoom命令对象
*/
public class LiveRoomCommand {
    @NotNull
    private LivePlatform platform;
    @NotNull
    private LiveChannel channel;
    @NotBlank
    @Size(max = 300)
    private String anchorUmAccount;
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    @Size(max = 300)
    private String backgroundImage;
    @NotBlank
    @Size(max = 300)
    private String shareImage;
    @NotBlank
    @Size(max = 300)
    private String promotionImage;
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private LiveRoomType roomType;
    private String content;
    private Map<String, Object> attributes;
    @NotNull
    private Boolean deleted;

    public LivePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(LivePlatform platform) {
        this.platform = platform;
    }

    public LiveChannel getChannel() {
        return channel;
    }

    public void setChannel(LiveChannel channel) {
        this.channel = channel;
    }

    public void setRoomType(LiveRoomType roomType) {
        this.roomType = roomType;
    }

    public String getAnchorUmAccount() {
        return this.anchorUmAccount;
    }

    public void setAnchorUmAccount(String anchorUmAccount) {
        this.anchorUmAccount = anchorUmAccount;
    }
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    public String getShareImage() {
        return this.shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }
    public String getPromotionImage() {
        return this.promotionImage;
    }

    public void setPromotionImage(String promotionImage) {
        this.promotionImage = promotionImage;
    }
    public Instant getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    public Instant getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public LiveRoomType getRoomType() {
        return roomType;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}