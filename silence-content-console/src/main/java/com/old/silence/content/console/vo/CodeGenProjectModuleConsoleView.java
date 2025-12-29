package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface CodeGenProjectModuleConsoleView extends AuditableView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getProjectId();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getModuleId();

    CodeGenModuleConsoleView getCodeGenModule();


}
