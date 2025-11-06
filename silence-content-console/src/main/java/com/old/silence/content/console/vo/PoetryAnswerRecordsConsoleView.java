package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.util.Map;

/**
* PoetryAnswerRecords视图接口
*/
public interface PoetryAnswerRecordsConsoleView extends AuditableView {
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