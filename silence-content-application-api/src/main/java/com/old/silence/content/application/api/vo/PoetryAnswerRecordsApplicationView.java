package com.old.silence.content.application.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;

/**
* PoetryAnswerRecords视图接口
*/
@ProjectedPayload
public interface PoetryAnswerRecordsApplicationView {
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