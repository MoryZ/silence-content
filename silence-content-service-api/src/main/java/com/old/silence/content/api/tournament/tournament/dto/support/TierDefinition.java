package com.old.silence.content.api.tournament.tournament.dto.support;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class TierDefinition {


    @NotBlank
    @Size(max = 20)
    private String tierName;
    private Integer order;
    @NotBlank
    private String tierImageUrl;

    @Valid
    private TierMapping tierMapping;


    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public Integer getsOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getTierImageUrl() {
        return tierImageUrl;
    }

    public void setTierImageUrl(String tierImageUrl) {
        this.tierImageUrl = tierImageUrl;
    }

    public TierMapping getTierMapping() {
        return tierMapping;
    }

    public void setTierMapping(TierMapping tierMapping) {
        this.tierMapping = tierMapping;
    }
}
