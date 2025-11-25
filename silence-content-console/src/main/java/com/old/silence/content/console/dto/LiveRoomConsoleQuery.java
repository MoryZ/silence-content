package com.old.silence.content.console.dto;


import java.time.Instant;
import java.util.Map;

import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;
import com.old.silence.content.domain.enums.LiveRoomType;

/**
* LiveRoom查询对象
*/
public class LiveRoomConsoleQuery {
    private LivePlatform platform;
    private LiveChannel channel;
    private String anchorUmAccount;

    private String title;

    private String backgroundImage;

    private String shareImage;

    private String promotionImage;


    private Instant startTimeStart;

    private Instant startTimeEnd;

    private Instant endTimeStart;

    private Instant endTimeEnd;
    private LiveRoomType roomType;
    private String content;

    private Map<String, Object> attributes;


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

    public String getAnchorUmAccount() {
        return anchorUmAccount;
    }

    public void setAnchorUmAccount(String anchorUmAccount) {
        this.anchorUmAccount = anchorUmAccount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public String getPromotionImage() {
        return promotionImage;
    }

    public void setPromotionImage(String promotionImage) {
        this.promotionImage = promotionImage;
    }

    public Instant getStartTimeStart() {
        return startTimeStart;
    }

    public void setStartTimeStart(Instant startTimeStart) {
        this.startTimeStart = startTimeStart;
    }

    public Instant getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(Instant startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public Instant getEndTimeStart() {
        return endTimeStart;
    }

    public void setEndTimeStart(Instant endTimeStart) {
        this.endTimeStart = endTimeStart;
    }

    public Instant getEndTimeEnd() {
        return endTimeEnd;
    }

    public void setEndTimeEnd(Instant endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }

    public LiveRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(LiveRoomType roomType) {
        this.roomType = roomType;
    }

    public String getContent() {
        return content;
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
}