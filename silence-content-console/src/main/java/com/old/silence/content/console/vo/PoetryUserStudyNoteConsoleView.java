package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * PoetryUserStudyNote视图接口
 */
public interface PoetryUserStudyNoteConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getContentId();

    String getNoteContent();

    String getTags();

    String getDisclosure();

}