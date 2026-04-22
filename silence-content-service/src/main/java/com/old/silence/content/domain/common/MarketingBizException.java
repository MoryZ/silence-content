package com.old.silence.content.domain.common;


import com.old.silence.core.exception.SilenceException;

/**
 * @author yangwenchang
 * @date 2023-03-01
 * @description
 */
public class MarketingBizException extends SilenceException {

    private static final long serialVersionUID = -8062198192996521965L;

    public MarketingBizException(BizErrorCode bizErrorCode) {
        super(bizErrorCode.getHttpStatusCode(), bizErrorCode.getCode(), bizErrorCode.getMessage());
    }

    public MarketingBizException(BizErrorCode bizErrorCode, String message) {
        super(bizErrorCode.getHttpStatusCode(), bizErrorCode.getCode(), message);
    }
}
