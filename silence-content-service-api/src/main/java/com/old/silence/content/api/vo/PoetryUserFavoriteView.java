package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserFavorite视图接口
 */
@ProjectedPayload
public interface PoetryUserFavoriteView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getContentId();

}