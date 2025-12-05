package com.old.silence.content.api.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.codegen.BuildTool;
import com.old.silence.content.domain.enums.codegen.ProjectLanguage;
import com.old.silence.content.domain.enums.codegen.ProjectType;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenProjectView {

    BigInteger getId();

    String getProjectName();

    ProjectType getProjectType();

    String getDisplayName();

    String getDescription();

    String getBaseDirectory();

    String getRepoUrl();

    String getOwner();

    ProjectLanguage getLanguage();

    BuildTool getBuildTool();


}
