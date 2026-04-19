package com.old.silence.content.api.tournament.tournament.dto;




import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.enums.ParticipantType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

public class TournamentMarketingEventCacheDto {

    //营销活动ID
    private BigInteger id;

    // 活动编码
    private String code;

    // 活动名称
    private String name;

    // 活动展示名称
    private String displayName;

    // 活动说明
    private String description;

    // 活动开始时间
    private Instant startTime;

    // 活动结束时间
    private Instant endTime;

    // 封面图片
    private String coverUrl;

    // 状态
    private MarketingEventStatus status;

    // 背景图地址
    private String backgroundImageUrl;

    //活动规则内容
    private String ruleContent;

    // 活动参与者ID
    private BigInteger participantId;

    // 活动参与者类型
    private ParticipantType participantType;

    // 扩展属性
    private Map<String, Object> attributes;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public MarketingEventStatus getStatus() {
        return status;
    }

    public void setStatus(MarketingEventStatus status) {
        this.status = status;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
