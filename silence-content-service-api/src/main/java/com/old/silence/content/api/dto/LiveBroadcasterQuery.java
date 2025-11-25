package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.LiveBroadcasterRoleType;
import com.old.silence.content.domain.enums.LiveChannel;
import com.old.silence.content.domain.enums.LivePlatform;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;


/**
* LiveBroadcaster查询对象
*/
public class LiveBroadcasterQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private LivePlatform platform;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private LiveChannel channel;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String channelAppid;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private LiveBroadcasterRoleType roleType;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String umAccount;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String nickname;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String wechatId;


    public LivePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(LivePlatform platform) {
        this.platform = platform;
    }

    public LiveChannel getChannel() {
        return channel;
    }

    public void setChannel(LiveChannel channel) {
        this.channel = channel;
    }

    public String getChannelAppid() {
        return channelAppid;
    }

    public void setChannelAppid(String channelAppid) {
        this.channelAppid = channelAppid;
    }

    public LiveBroadcasterRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(LiveBroadcasterRoleType roleType) {
        this.roleType = roleType;
    }

    public String getUmAccount() {
        return umAccount;
    }

    public void setUmAccount(String umAccount) {
        this.umAccount = umAccount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }
}