package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.LiveBroadcasterRoleType;
import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;
import com.old.silence.data.commons.domain.AuditableView;


/**
* LiveBroadcaster视图接口
*/
@ProjectedPayload
public interface LiveBroadcasterView extends AuditableView {
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