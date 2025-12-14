package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * 代码文件规格查询条件
 *
 * @author moryzang
 */
public class CodeFileSpecQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String templateName;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String moduleType;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String displayName;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String generationCondition;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Boolean enabled;

    public CodeFileSpecQuery() {
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGenerationCondition() {
        return generationCondition;
    }

    public void setGenerationCondition(String generationCondition) {
        this.generationCondition = generationCondition;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "CodeFileSpecQuery{" +
                "templateName='" + templateName + '\'' +
                ", moduleType='" + moduleType + '\'' +
                ", generationCondition='" + generationCondition + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
