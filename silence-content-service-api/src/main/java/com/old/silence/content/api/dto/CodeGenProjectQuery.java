package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.codegen.ProjectType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * @author moryzang
 */
public class CodeGenProjectQuery {

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String projectName;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private ProjectType projectType;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
}
