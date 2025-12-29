package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.util.Map;


/**
 * CodeApiDocument视图接口
 */
@ProjectedPayload
public interface CodeApiDocumentView extends AuditableView {
    BigInteger getId();

    String getTableName();

    String getApiName();

    Map<String, Object> getDetail();


}