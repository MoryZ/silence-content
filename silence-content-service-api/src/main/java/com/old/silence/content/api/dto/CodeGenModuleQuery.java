package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * @author moryzang
 */
public class CodeGenModuleQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String moduleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }


}
