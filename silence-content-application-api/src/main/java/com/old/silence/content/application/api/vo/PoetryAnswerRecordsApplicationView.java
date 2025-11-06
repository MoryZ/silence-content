package com.old.silence.content.application.api.vo;

import java.math.BigInteger;
import java.util.Map;

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

    Map<String, Object> getUserAnswer();

    Boolean getCorrect();

    Long getHintsUsed();

    String getSessionId();

    Long getResponseTime();


}