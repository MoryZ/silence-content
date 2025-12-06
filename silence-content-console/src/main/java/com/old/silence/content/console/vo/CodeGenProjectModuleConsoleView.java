package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.data.commons.domain.AuditableView;

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

}
