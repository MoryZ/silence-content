package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* PoetryAnswerRecords视图接口
*/
@ProjectedPayload
public interface PoetryAnswerRecordsView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getQuizId();

    BigInteger getContentId();

    String getUserAnswer();

    Boolean getCorrect();

    Long getHintsUsed();

    String getSessionId();

    Long getResponseTime();


}