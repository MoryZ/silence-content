package com.old.silence.content.api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.old.silence.content.domain.enums.MarketingRuleType;

import java.math.BigInteger;
import java.util.Map;

public class MarketingRuleDomainCommand {

    private BigInteger id;

    private String name;

    private MarketingRuleType type;

    @JsonTypeInfo(use = Id.NAME, include = As.EXTERNAL_PROPERTY, property = "type")
    @JsonSubTypes({ @Type(value = GameTaskFrequencyRuleDomainCommand.class, name = "4")})
    private Map<String, Object> content;

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

    public MarketingRuleType getType() {
        return type;
    }

    public void setType(MarketingRuleType type) {
        this.type = type;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
