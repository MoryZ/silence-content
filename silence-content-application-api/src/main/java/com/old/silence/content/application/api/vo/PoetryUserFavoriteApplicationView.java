package com.old.silence.content.application.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserFavorite视图接口
 */
@ProjectedPayload
public interface PoetryUserFavoriteApplicationView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getContentId();

}