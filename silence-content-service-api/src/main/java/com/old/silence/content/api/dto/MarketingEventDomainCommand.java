package com.old.silence.content.api.dto;


import com.old.silence.content.domain.enums.BusinessScene;
import com.old.silence.content.domain.enums.MarketingType;
import com.old.silence.content.domain.enums.ParticipantType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

public class MarketingEventDomainCommand {

    private BigInteger id;

    private String name;

    private String code;

    private BusinessScene businessScene;

    private String displayName;

    private String description;

    private Instant startTime;

    private Instant endTime;

    private String coverUrl;

    private String backgroundImageIobsKey;

    private String ruleContent;

    private MarketingRuleDomainCommand entryRule;

    private BigInteger entryRuleId;

    private ParticipantType participantType;

    private Map<String, Object> attributes;

    /**
     * 所属机构
     */
    private String regionCode;

    /**
     * 活动分类
     */
    private MarketingType categoryCode;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessScene getBusinessScene() {
        return businessScene;
    }

    public void setBusinessScene(BusinessScene businessScene) {
        this.businessScene = businessScene;
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

    public MarketingRuleDomainCommand getEntryRule() {
        return entryRule;
    }

    public void setEntryRule(MarketingRuleDomainCommand entryRule) {
        this.entryRule = entryRule;
    }

    public String getBackgroundImageIobsKey() {
        return backgroundImageIobsKey;
    }

    public void setBackgroundImageIobsKey(String backgroundImageIobsKey) {
        this.backgroundImageIobsKey = backgroundImageIobsKey;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public BigInteger getEntryRuleId() {
        return entryRuleId;
    }

    public void setEntryRuleId(BigInteger entryRuleId) {
        this.entryRuleId = entryRuleId;
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

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public MarketingType getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(MarketingType categoryCode) {
        this.categoryCode = categoryCode;
    }
}
