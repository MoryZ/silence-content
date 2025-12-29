package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.util.Map;

/**
 * PoetryAnswerRecords视图接口
 */
@ProjectedPayload
public interface PoetryAnswerRecordsView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getQuizId();

    BigInteger getSubCategoryId();

    BigInteger getContentId();

    Map<String, Object> getUserAnswer();

    Boolean getCorrect();

    Long getHintsUsed();

    String getSessionId();

    Long getResponseTime();


}