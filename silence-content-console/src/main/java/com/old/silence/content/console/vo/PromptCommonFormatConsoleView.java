package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
* PromptCommonFormat视图接口
*/
public interface PromptCommonFormatConsoleView extends AuditableView {
    BigInteger getId();

    String getFormatName();
    String getFormatContent();
    String getDescription();
    Boolean getActive();

}