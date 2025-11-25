package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;
import com.old.silence.content.domain.enums.LiveRoomType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

/**
* LiveRoom视图接口
*/
public interface LiveRoomConsoleView extends AuditableView {
    BigInteger getId();

    LivePlatform getPlatform();
    LiveChannel getChannel();
    String getAnchorUmAccount();
    String getTitle();
    String getBackgroundImage();
    String getShareImage();
    String getPromotionImage();
    Instant getStartTime();
    Instant getEndTime();
    LiveRoomType getRoomType();
    String getContent();
    Map<String, Object> getAttributes();
    Boolean getDeleted();

}