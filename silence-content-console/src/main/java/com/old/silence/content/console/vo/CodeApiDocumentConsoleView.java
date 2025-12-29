package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
* CodeApiDocument视图接口
*/
public interface CodeApiDocumentConsoleView extends AuditableView {
    BigInteger getId();

    String getTableName();
    String getApiName();
    String getDetail();

}