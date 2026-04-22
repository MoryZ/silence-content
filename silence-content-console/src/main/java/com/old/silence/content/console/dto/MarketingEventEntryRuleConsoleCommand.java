package com.old.silence.content.console.dto;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public class MarketingEventEntryRuleConsoleCommand {
    private String realNameAuthenticationEnable;
    private String riskControlEnable;
    private Set<BigInteger> exclusionEventGameIds;

    /**
     * 所属机构
     */
    private List<String> regionCodes;

    public String getRealNameAuthenticationEnable() {
        return realNameAuthenticationEnable;
    }

    public void setRealNameAuthenticationEnable(String realNameAuthenticationEnable) {
        this.realNameAuthenticationEnable = realNameAuthenticationEnable;
    }

    public String getRiskControlEnable() {
        return riskControlEnable;
    }

    public void setRiskControlEnable(String riskControlEnable) {
        this.riskControlEnable = riskControlEnable;
    }

    public Set<BigInteger> getExclusionEventGameIds() {
        return exclusionEventGameIds;
    }
    public void setExclusionEventGameIds(Set<BigInteger> exclusionEventGameIds) {
        this.exclusionEventGameIds = exclusionEventGameIds;
    }

    public List<String> getRegionCodes() {
        return regionCodes;
    }

    public void setRegionCodes(List<String> regionCodes) {
        this.regionCodes = regionCodes;
    }
}