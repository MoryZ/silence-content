package com.old.silence.content.console.vo;

import java.math.BigInteger;
import java.util.List;

import com.old.silence.content.api.vo.CodeGenProjectModuleView;
import com.old.silence.content.domain.enums.codegen.BuildTool;
import com.old.silence.content.domain.enums.codegen.ProjectLanguage;
import com.old.silence.content.domain.enums.codegen.ProjectType;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * @author moryzang
 */
public interface CodeGenProjectConsoleView extends AuditableView {

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

    List<CodeGenProjectModuleConsoleView> getCodeGenProjectModules();

}
