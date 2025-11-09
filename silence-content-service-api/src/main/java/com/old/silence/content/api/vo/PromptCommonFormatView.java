package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
* PromptCommonFormat视图接口
*/
@ProjectedPayload
public interface PromptCommonFormatView extends AuditableView {
    BigInteger getId();

    String getFormatName();

    String getFormatContent();

    String getDescription();

    Boolean getActive();


}