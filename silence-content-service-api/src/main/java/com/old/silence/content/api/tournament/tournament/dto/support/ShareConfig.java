package com.old.silence.content.api.tournament.tournament.dto.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class ShareConfig {

    /**
     * 分享图片
     */
    @NotBlank
    private String shareImageUrl;

    /**
     * 分享链接
     */
    private String shareLink;

    /**
     * 分享标题
     */
    @NotBlank
    private String shareTitle;

    /**
     * 分享类型
     */
    @NotNull
    private Integer shareType;

    public String getShareImageUrl() {
        return shareImageUrl;
    }

    public void setShareImageUrl(String shareImageUrl) {
        this.shareImageUrl = shareImageUrl;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }
}
