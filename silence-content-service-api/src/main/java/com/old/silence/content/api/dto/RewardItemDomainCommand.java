package com.old.silence.content.api.dto;

import org.apache.commons.lang3.StringUtils;
import com.old.silence.content.domain.enums.RewardItemType;
import com.old.silence.content.domain.enums.TimeCalculationMethod;

import java.util.Map;


/**
 * @author yangwenchang
 */
public class RewardItemDomainCommand {

    private String name;

    private String displayName;

    private RewardItemType type;

    private Integer quantity;

    private Map<String, Object> attributes;

    private String iconKey;

    private String iconUrl;

    private String description;

    private boolean claimRequired;

    private TimeCalculationMethod expirationTimeCalculationMethod;

    private String expirationTimeCalculationValue;

    private String marketingPlansCommands;

    private String productPlanCommand;

    private String referenceId;

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

    public RewardItemType getType() {
        return type;
    }

    public void setType(RewardItemType type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getIconKey() {
        return iconKey;
    }

    public void setIconKey(String iconKey) {
        this.iconKey = iconKey;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isClaimRequired() {
        return claimRequired;
    }

    public void setClaimRequired(boolean claimRequired) {
        this.claimRequired = claimRequired;
    }

    public TimeCalculationMethod getExpirationTimeCalculationMethod() {
        return expirationTimeCalculationMethod;
    }

    public void setExpirationTimeCalculationMethod(TimeCalculationMethod expirationTimeCalculationMethod) {
        this.expirationTimeCalculationMethod = expirationTimeCalculationMethod;
    }

    public String getExpirationTimeCalculationValue() {
        return expirationTimeCalculationValue;
    }

    public void setExpirationTimeCalculationValue(String expirationTimeCalculationValue) {
        this.expirationTimeCalculationValue = expirationTimeCalculationValue;
    }

    public String getMarketingPlansCommands() {
        return marketingPlansCommands;
    }

    public void setMarketingPlansCommands(String marketingPlansCommands) {
        this.marketingPlansCommands = marketingPlansCommands;
    }

    public String getProductPlanCommand() {
        return productPlanCommand;
    }

    public void setProductPlanCommand(String productPlanCommand) {
        this.productPlanCommand = productPlanCommand;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }


}
