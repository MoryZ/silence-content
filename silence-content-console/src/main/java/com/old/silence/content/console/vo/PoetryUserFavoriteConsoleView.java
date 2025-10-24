package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserFavorite视图接口
 */
public interface PoetryUserFavoriteConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getContentId();

}