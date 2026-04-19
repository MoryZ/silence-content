package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


import com.old.silence.content.domain.enums.BusinessScene;
import com.old.silence.content.domain.enums.MarketingType;
import com.old.silence.content.domain.enums.ParticipantType;
import com.old.silence.validation.group.CreationValidation;
import com.old.silence.validation.group.UpdateValidation;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

public class MarketingEventConsoleCommand {

    private BigInteger id;

    @NotBlank
    @Length(max = 64)
    private String name;

    @NotBlank
    @Length(max = 100)
    private String code;

    private BusinessScene businessScene;

    @Length(max = 64)
    private String displayName;

    @NotBlank
    @Length(max = 255)
    private String description;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    private String coverUrl;

    private String backgroundImageUrl;

    private ParticipantType participantType;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @NotEmpty(groups = {CreationValidation.class, UpdateValidation.class})
    private String ruleContent;

    private BigInteger entryRuleId;
    private MarketingEventEntryRuleConsoleCommand  entryRuleContent;

    private Map<String, Object> attributes;

    /**
     * 机构编码
     */
    private String regionCode;

    /**
     * 活动分类
     */
    private MarketingType categoryCode;

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

    public BigInteger getEntryRuleId() {
        return entryRuleId;
    }

    public void setEntryRuleId(BigInteger entryRuleId) {
        this.entryRuleId = entryRuleId;
    }

    public MarketingEventEntryRuleConsoleCommand getEntryRuleContent() {
        return entryRuleContent;
    }

    public void setEntryRuleContent(MarketingEventEntryRuleConsoleCommand entryRuleContent) {
        this.entryRuleContent = entryRuleContent;
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
