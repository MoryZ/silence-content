package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * PoetryUserFavorite视图接口
 */
public interface PoetryUserFavoriteConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getContentId();

}