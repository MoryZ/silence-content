package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;

/**
* CodeTableMeta视图接口
*/
public interface CodeTableMetaConsoleView extends AuditableView {
    BigInteger getId();

    String getTableName();
    String getSchemaName();
    String getComment();
    String getDetail();

}