package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryCategory视图接口
 */
public interface PoetryCategoryConsoleView extends AuditableView {
    BigInteger getId();

    String getName();

    String getCode();

    String getIcon();

    Long getSortOrder();

    BigInteger getParentId();

}