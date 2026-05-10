package com.old.silence.content.domain.enums;

/**
 * 内容互动处理模式：
 * - STATEFUL: 点赞 / 取消点赞 / 收藏 / 取消收藏
 * - STATELESS: 浏览 / 分享
 */
public enum ContentInteractionMode {
    STATEFUL,
    STATELESS
}