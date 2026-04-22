package com.old.silence.content.console.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.old.silence.content.domain.enums.RewardItemType;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class EventGameRewardItemRuleConsoleCommand {
    //增加奖品类型 奖品名称 奖品显示名称 数量限制类型 奖品图片 中奖区间
    @NotNull
    protected RewardItemType type;
    @NotBlank
    protected String name;
    @NotBlank
    protected String displayName;
    @NotNull
    protected Boolean limitQuantity;
    @NotBlank
    protected String iconUrl;
    @NotNull
    @Positive
    protected Integer rangeStart;
    @NotNull
    @Positive
    @Max(99999)
    protected Integer rangeEnd;

    public RewardItemType getType() {
        return type;
    }

    public void setType(RewardItemType type) {
        this.type = type;
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

    public Boolean getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(Boolean limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Integer rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }


}
