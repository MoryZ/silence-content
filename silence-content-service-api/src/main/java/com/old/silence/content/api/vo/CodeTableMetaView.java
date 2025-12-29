package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.util.Map;

/**
 * CodeTableMeta视图接口
 */
@ProjectedPayload
public interface CodeTableMetaView extends AuditableView {
    BigInteger getId();

    String getTableName();

    String getSchemaName();

    String getComment();

    Map<String, Object> getDetail();

}