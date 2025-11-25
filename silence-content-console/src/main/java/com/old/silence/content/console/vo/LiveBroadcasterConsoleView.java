package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.LiveBroadcasterRoleType;
import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;
import com.old.silence.data.commons.domain.AuditableView;


/**
* LiveBroadcaster视图接口
*/
public interface LiveBroadcasterConsoleView extends AuditableView {
    BigInteger getId();

    LivePlatform getPlatform();

    LiveChannel getChannel();

    String getChannelAppid();

    LiveBroadcasterRoleType getRoleType();

    String getUmAccount();

    String getNickname();

    String getWechatId();

    Boolean getDeleted();

}