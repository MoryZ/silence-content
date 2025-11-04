package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* PoetryAnswerRecords视图接口
*/
public interface PoetryAnswerRecordsConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();
    BigInteger getQuizId();
    BigInteger getContentId();
    String getUserAnswer();
    String getCorrect();
    Long getHintsUsed();
    String getSessionId();
    Long getResponseTime();

}