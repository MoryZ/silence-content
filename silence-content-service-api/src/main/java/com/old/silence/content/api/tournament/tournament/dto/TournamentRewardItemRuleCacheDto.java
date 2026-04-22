package com.old.silence.content.api.tournament.tournament.dto;


import com.old.silence.content.domain.enums.MarketingRuleType;

import java.math.BigInteger;

public class TournamentRewardItemRuleCacheDto {

    // 规则ID
    private BigInteger id;

    // 规则名称
    private String name;

    // 规则类型
    private MarketingRuleType type;

    // 规则配置内容
    private Object content;

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

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public <T> T unwrapContent(Class<T> type) {
        if (content == null) {
            return null;
        }
        if (!type.isAssignableFrom(content.getClass())) {
            throw new IllegalArgumentException(
                    "MarketingRule attributes type [" + content.getClass() + "] can not cast to [" + type + ']');
        }
        return type.cast(content);
    }
  }
