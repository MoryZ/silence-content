package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* PoetryUserFavorite视图接口
*/
@ProjectedPayload
public interface PoetryUserFavoriteView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();
    BigInteger getContentId();

}